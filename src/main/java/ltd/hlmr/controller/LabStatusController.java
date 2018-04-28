package ltd.hlmr.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.hlmr.po.LabStatus;
import ltd.hlmr.po.LabStatusId;
import ltd.hlmr.repository.LabStatusRepository;

@RestController
@RequestMapping("/lab/status")
@Api(tags = "实验室状态")
public class LabStatusController {
	@Autowired
	private LabStatusRepository labStatusRepository;

	@GetMapping
	@ApiOperation(value = "查询七天内的实验室状态")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "sort", value = "排序", required = true, paramType = "query", defaultValue = "idBookingDate,asc") })
	public List<LabStatus> findList(String name, @ApiParam(value = "排序") Sort sort) {
		String now = LocalDate.now().toString();
		return labStatusRepository.findByIdLabNameAndIdBookingDateGreaterThanEqual(name, now, sort);
	}

	@PutMapping
	@ApiOperation(value = "预约实验室")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public String chooseLabStatus(@AuthenticationPrincipal UserDetails userDetails,LabStatusId labStatusId, String teacherName) {
		return "预约实验室成功";
	}
}
