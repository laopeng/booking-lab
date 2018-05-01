package ltd.hlmr.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.hlmr.po.LabStatus;
import ltd.hlmr.po.LabStatus.Status;
import ltd.hlmr.po.LabStatus.Audit;
import ltd.hlmr.po.Student;
import ltd.hlmr.po.Teacher;
import ltd.hlmr.repository.LabStatusRepository;
import ltd.hlmr.repository.StudentRepository;
import ltd.hlmr.repository.TeacherRepository;

@RestController
@RequestMapping("/lab/status")
@Api(tags = "实验室状态")
public class LabStatusController {
	@Autowired
	private LabStatusRepository labStatusRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private TeacherRepository teacherRepository;

	@GetMapping
	@ApiOperation(value = "查询七天内的实验室状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "sort", value = "排序", required = true, paramType = "query", defaultValue = "idBookingDate,asc") })
	public List<LabStatus> findList(String name, @ApiParam(value = "排序") Sort sort) {
		String now = LocalDate.now().toString();
		return labStatusRepository.findByIdLabNameAndIdBookingDateGreaterThanEqual(name, now, sort);
	}

	@GetMapping("/current")
	@ApiOperation(value = "查询当前学生的选课情况")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public LabStatus findCurrent(@AuthenticationPrincipal UserDetails userDetails) {
		Student student = studentRepository.findByUserUsername(userDetails.getUsername());
		String now = LocalDate.now().toString();
		List<LabStatus> list = labStatusRepository.findByStudentAndIdBookingDateGreaterThanEqual(student, now);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@DeleteMapping("/current")
	@ApiOperation(value = "取消当前用户的预约")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public String deleteCurrent(@AuthenticationPrincipal UserDetails userDetails) {
		Student student = studentRepository.findByUserUsername(userDetails.getUsername());
		String now = LocalDate.now().toString();
		List<LabStatus> list = labStatusRepository.findByStudentAndIdBookingDateGreaterThanEqual(student, now);
		for (LabStatus e : list) {
			e.setStatus(Status.可用);
			e.setStudent(null);
			e.setTeacher(null);
			e.setAudit(null);
		}
		labStatusRepository.save(list);
		return "取消预约成功";
	}

	@PutMapping
	@ApiOperation(value = "预约实验室")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public String chooseLabStatus(@AuthenticationPrincipal UserDetails userDetails, @RequestBody LabStatus labStatus) {
		synchronized (labStatus) {
			Student student = studentRepository.findByUserUsername(userDetails.getUsername());
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

		return "预约实验室成功";
	}
}
