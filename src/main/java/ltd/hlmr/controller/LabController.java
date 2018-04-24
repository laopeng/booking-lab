package ltd.hlmr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ltd.hlmr.po.Lab;
import ltd.hlmr.repository.LabRepository;

@RestController
@RequestMapping("/labs")
@Api(tags = "实验室")
public class LabController {
	@Autowired
	private LabRepository labRepository;

	@GetMapping
	@ApiOperation(value = "查询实验室列表")
	public List<Lab> findList() {
		return labRepository.findAll();
	}
}
