package ltd.hlmr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import ltd.hlmr.po.Teacher;
import ltd.hlmr.repository.TeacherRepository;

@RestController
@RequestMapping("/teachers")
@Api(tags = "教师管理")
public class TeacherController {
	@Autowired
	private TeacherRepository teacherRepository;

	@GetMapping
	@ApiOperation("查询教师列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public List<Teacher> getTeacherList() {
		return teacherRepository.findAll();
	}
}
