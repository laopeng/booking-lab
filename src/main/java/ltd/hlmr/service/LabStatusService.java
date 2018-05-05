package ltd.hlmr.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ltd.hlmr.po.LabStatus;
import ltd.hlmr.po.LabStatusLog;
import ltd.hlmr.po.Student;
import ltd.hlmr.po.Teacher;
import ltd.hlmr.po.LabStatus.Audit;
import ltd.hlmr.po.LabStatus.Status;
import ltd.hlmr.repository.LabStatusLogRepository;
import ltd.hlmr.repository.LabStatusRepository;
import ltd.hlmr.repository.StudentRepository;
import ltd.hlmr.repository.TeacherRepository;
import ltd.hlmr.util.IDGenerator;

@Service
public class LabStatusService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private LabStatusRepository labStatusRepository;

	@Autowired
	private LabStatusLogRepository labStatusLogRepository;

	@Autowired
	private MailService mailService;

	/**
	 * 学生预约实验室
	 * 
	 * @param userDetails
	 * @param labStatus
	 */
	@Transactional
	public void chooseLabStatus(UserDetails userDetails, LabStatus labStatus) {
		synchronized (labStatus) {
			Student student = studentRepository.findByUserUsername(userDetails.getUsername());
			if (student.getIsDisable() != null && student.getIsDisable()) {
				throw new RuntimeException(
						"因取消了通过审核的预约，你已被禁止在" + student.getDisableDateTime().toString() + "之前进行预约实验室！");
			}
			String now = LocalDate.now().toString();
			List<LabStatus> list = labStatusRepository.findByStudentAndIdBookingDateGreaterThanEqual(student, now);
			if (!list.isEmpty()) {
				LabStatus current = list.get(0);
				if (Audit.通过.equals(current.getAudit())) {
					throw new RuntimeException(
							"你预约的【" + current.getId().getLab().getName() + "】已通过审核，不可在三天内重新预约其他实验室。");
				}
			}

			// 判断实验室是否可选
			LabStatus labStatus2 = labStatusRepository.findOne(labStatus.getId());
			if (labStatus2.getStatus().equals(Status.不可用)) {
				throw new RuntimeException("你慢了一步，此实验已经被其他同学先预约了！");
			}

			// 把该学生其他预约重置为可选状态
			for (LabStatus e : list) {
				e.setStatus(Status.可用);
				e.setStudent(null);
				e.setTeacher(null);
				e.setAudit(null);
			}
			labStatusRepository.save(list);

			// 选课
			Teacher teacher = teacherRepository.findByName(labStatus.getTeacher().getName());
			labStatus2.setStatus(Status.不可用);
			labStatus2.setStudent(student);
			labStatus2.setTeacher(teacher);
			labStatus2.setAudit(Audit.未审);
			labStatusRepository.save(labStatus2);
		}
	}

	/**
	 * 学生取消预约
	 * 
	 * @param userDetails
	 */
	@Transactional
	public void deleteCurrent(UserDetails userDetails) {
		Student student = studentRepository.findByUserUsername(userDetails.getUsername());
		String now = LocalDate.now().toString();
		List<LabStatus> list = labStatusRepository.findByStudentAndIdBookingDateGreaterThanEqual(student, now);
		boolean hasAudit = false;
		for (LabStatus e : list) {
			if (e.getAudit().equals(Audit.通过)) {
				hasAudit = true;
				LabStatusLog labStatusLog = new LabStatusLog();
				labStatusLog.setId(IDGenerator.getId());
				labStatusLog.setStudentUsername(student.getName());
				labStatusLog.setLabStatus(e);
				labStatusLog.setAuditTime(new Date());
				labStatusLog.setContent("学生[" + student.getName() + "]取消了已通过审核的" + e.getId().getBookingDate()
						+ e.getId().getBookingTimeRang() + e.getId().getLab().getName() + "实验室(指导老师["
						+ e.getTeacher().getName() + "])!");
				String title = "学生[" + student.getUser().getUsername() + "]取消了审核通过的实验室预约";
				mailService.sendSimpleMail(e.getTeacher().getEmail(), title, labStatusLog.getContent());
				labStatusLogRepository.save(labStatusLog);
			}
			e.setStatus(Status.可用);
			e.setStudent(null);
			e.setTeacher(null);
			e.setAudit(null);
		}
		if (hasAudit) {
			student.setDisableDateTime(LocalDateTime.now().plusDays(2));
			studentRepository.save(student);
		}
		labStatusRepository.save(list);
	}

	/**
	 * 教师审核实验室使用情况
	 * 
	 * @param userDetails
	 * @param labStatus
	 */
	@Transactional
	public void auditLabStatus(UserDetails userDetails, LabStatus labStatus) {
		LabStatus labStatus2 = labStatusRepository.getOne(labStatus.getId());
		labStatus2.setAudit(labStatus.getAudit());

		Teacher teacher = teacherRepository.findByUserUsername(userDetails.getUsername());
		LabStatusLog labStatusLog = new LabStatusLog();
		labStatusLog.setId(IDGenerator.getId());
		labStatusLog.setStudentUsername(labStatus2.getStudent().getUser().getUsername());
		labStatusLog.setTeacherName(teacher.getName());
		labStatusLog.setLabStatus(labStatus2);
		labStatusLog.setAuditTime(new Date());
		String content = null;
		if (labStatus.getAudit().equals(Audit.通过)) {
			content = "学生[" + labStatus2.getStudent().getName() + "]预约" + labStatus2.getId().getBookingDate()
					+ labStatus2.getId().getBookingTimeRang() + "的" + labStatus2.getId().getLab().getName()
					+ "实验室（指导教师[" + labStatus2.getTeacher().getName() + "]），由管理员审核通过。";
			String title = "学生[" + labStatus2.getStudent().getName() + "]预约实验室审核通过";
			mailService.sendSimpleMail(labStatus2.getTeacher().getEmail(), title, content);
		} else {
			content = "学生[" + labStatus2.getStudent().getName() + "]预约" + labStatus2.getId().getBookingDate()
					+ labStatus2.getId().getBookingTimeRang() + "的" + labStatus2.getId().getLab().getName()
					+ "实验室（指导教师[" + labStatus2.getTeacher().getName() + "]），由管理员不予通过！";
			labStatus2.setStatus(Status.可用);
			labStatus2.setStudent(null);
			labStatus2.setTeacher(null);
			labStatus2.setAudit(null);
		}
		labStatusLog.setContent(content);

		labStatusLogRepository.save(labStatusLog);
		labStatusRepository.save(labStatus2);
	}
}
