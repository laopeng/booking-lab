package ltd.hlmr.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import ltd.hlmr.po.Student;
import ltd.hlmr.repository.StudentRepository;
import ltd.hlmr.service.StudentService;

@RestController
@RequestMapping("/students")
@Api(tags = "学生管理")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private StudentRepository studentRepository;

	@PostMapping
	@ApiOperation("新增学生")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public String add(@Valid @RequestBody Student student) {
		Student student2 = new Student();
		student2.setNumber(student.getNumber());
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("username", match -> match.ignoreCase());
		Example<Student> example = Example.of(student2, matcher);
		Student student3 = studentRepository.findOne(example);
		if (student3 != null) {
			throw new RuntimeException("此学生已经绑定了另一个微信帐号，请联系管理老师先进行解绑才能继续操作。");
		}
		studentService.createStudent(student);
		return "Success";
	}

	@GetMapping
	@ApiOperation(value = "查询学生列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "sort", value = "排序", paramType = "query", defaultValue = "idBookingDate,asc"),
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", defaultValue = "0"),
			@ApiImplicitParam(name = "size", value = "每页几条", paramType = "query", defaultValue = "20") })
	public Page<Student> findList(String name, Pageable pageable) {
		Student student = new Student();
		if (StringUtils.hasText(name)) {
			student.setName(name);
		}
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", match -> match.contains());
		Example<Student> example = Example.of(student, matcher);
		return studentRepository.findAll(example, pageable);
	}

	@GetMapping("/current")
	@ApiOperation("查询当前学生的信息")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public Student getCurrent(@AuthenticationPrincipal UserDetails userDetails) {
		Student student = studentRepository.findByUserUsername(userDetails.getUsername());
		return student;
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable String id) {
		studentService.delete(id);
		return "解绑成功";
	}
}
