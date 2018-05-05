package ltd.hlmr.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
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
import ltd.hlmr.po.Student;
import ltd.hlmr.repository.LabStatusRepository;
import ltd.hlmr.repository.StudentRepository;
import ltd.hlmr.service.LabStatusService;

@RestController
@RequestMapping("/lab/status")
@Api(tags = "实验室状态")
public class LabStatusController {
	@Autowired
	private LabStatusRepository labStatusRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private LabStatusService labStatusService;

	@GetMapping("/all")
	@ApiOperation(value = "查询所有实验室状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "sort", value = "排序", paramType = "query", defaultValue = "idBookingDate,asc"),
			@ApiImplicitParam(name = "page", value = "第几页", paramType = "query", defaultValue = "0"),
			@ApiImplicitParam(name = "size", value = "每页几条", paramType = "query", defaultValue = "20") })
	public Page<LabStatus> findAllList(String labId, Pageable pageable) {
		if (StringUtils.hasText(labId)) {
			return labStatusRepository.findByIdLabId(labId, pageable);
		}
		return labStatusRepository.findAll(pageable);
	}

	@GetMapping
	@ApiOperation(value = "查询七天内的实验室状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "sort", value = "排序", required = true, paramType = "query", defaultValue = "idBookingDate,asc") })
	public List<LabStatus> findList(String name, @ApiParam(value = "排序") Sort sort) {
		String now = LocalDate.now().toString();
		return labStatusRepository.findByIdLabNameAndIdBookingDateGreaterThanEqual(name, now, sort);
	}

	@PutMapping("/audit")
	public String auditLabStatus(@AuthenticationPrincipal UserDetails userDetails, @RequestBody LabStatus labStatus) {
		labStatusService.auditLabStatus(userDetails, labStatus);
		return "操作成功！";
	}

	@GetMapping("/current")
	@ApiOperation(value = "查询当前学生的预约情况")
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
		labStatusService.deleteCurrent(userDetails);
		return "取消预约成功";
	}

	@PutMapping
	@ApiOperation(value = "预约实验室")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public String chooseLabStatus(@AuthenticationPrincipal UserDetails userDetails, @RequestBody LabStatus labStatus) {
		labStatusService.chooseLabStatus(userDetails, labStatus);
		return "预约实验室成功";
	}
}
