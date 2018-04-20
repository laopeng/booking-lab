package ltd.hlmr.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ltd.hlmr.po.Student;
import ltd.hlmr.po.User;
import ltd.hlmr.repository.StudentRepository;
import ltd.hlmr.util.IDGenerator;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

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
}
