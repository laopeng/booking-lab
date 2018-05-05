package ltd.hlmr.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ltd.hlmr.po.LabStatus;
import ltd.hlmr.po.Student;
import ltd.hlmr.po.User;
import ltd.hlmr.po.LabStatus.Status;
import ltd.hlmr.repository.LabStatusRepository;
import ltd.hlmr.repository.StudentRepository;
import ltd.hlmr.util.IDGenerator;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private LabStatusRepository labStatusRepository;

	/**
	 * 创建学生帐号
	 * 
	 * @param student
	 */
	@Transactional
	public void createStudent(Student student) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String studentId = IDGenerator.getId();
		student.setId(studentId);
		User user = student.getUser();
		user.setId(studentId);
		user.setPassword(passwordEncoder.encode(user.getUsername()));
		user.setDescription("学生帐号");
		user.setType("S");
		user.setCreateDate(new Date());
		user.setStatus("A");
		studentRepository.save(student);
	}

	@Transactional
	public void delete(String id) {
		List<LabStatus> list = labStatusRepository.findByStudentId(id);
		// 把该学生所有预约重置为可选状态
		for (LabStatus e : list) {
			e.setStatus(Status.可用);
			e.setStudent(null);
			e.setTeacher(null);
			e.setAudit(null);
		}
		labStatusRepository.save(list);
		studentRepository.delete(id);
	}
}
