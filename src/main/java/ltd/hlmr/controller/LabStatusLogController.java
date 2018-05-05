package ltd.hlmr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import ltd.hlmr.po.LabStatusLog;
import ltd.hlmr.repository.LabStatusLogRepository;

@RestController
@RequestMapping("/labStatusLogs")
@Api(tags = "预约实验室审核日志")
public class LabStatusLogController {
	@Autowired
	private LabStatusLogRepository labStatusLogRepository;

	@GetMapping("/current")
	@ApiOperation(value = "根据学生信息查询审核日志")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public LabStatusLog findList(@AuthenticationPrincipal UserDetails userDetails) {
		Pageable pageable = new PageRequest(0, 5, Direction.DESC, "auditTime");
		List<LabStatusLog> list = labStatusLogRepository.findByStudentUsername(userDetails.getUsername(), pageable)
				.getContent();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}
