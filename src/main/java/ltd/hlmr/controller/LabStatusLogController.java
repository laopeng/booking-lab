package ltd.hlmr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import ltd.hlmr.po.LabStatusId;
import ltd.hlmr.po.LabStatusLog;
import ltd.hlmr.repository.LabStatusLogRepository;

@RestController
@RequestMapping("/labStatusLogs")
@Api(tags = "预约实验室审核日志")
public class LabStatusLogController {
	@Autowired
	private LabStatusLogRepository labStatusLogRepository;

	@GetMapping
	@ApiOperation(value = "根据预约实验室id查询日志列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public List<LabStatusLog> findList(LabStatusId labStatusId) {
		return labStatusLogRepository.findByLabStatusId(labStatusId);
	}
}
