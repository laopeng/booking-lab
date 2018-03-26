package ltd.hlmr.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import ltd.hlmr.po.Role;
import ltd.hlmr.repository.RoleRepository;
import ltd.hlmr.util.IDGenerator;

@RestController
@RequestMapping("/sys/roles")
@Api(tags = "角色管理")
public class RoleController {
	@Autowired
	private RoleRepository roleRepository;

	public final static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@PostMapping
	@ApiOperation("新增角色")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public String add(@RequestBody Role role) {
		role.setId(IDGenerator.getId());
		role.setCreateDate(new Date());
		roleRepository.save(role);
		return "新增成功";
	}

	@DeleteMapping("/{roleId}")
	@ApiOperation("根据角色id删除角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "roleId", value = "角色id", paramType = "path", required = true) })
	public String delete(@PathVariable String roleId) {
		roleRepository.delete(roleId);
		return "删除成功";
	}

	@DeleteMapping
	@ApiOperation("根据角色id列表删除角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "roleIds", value = "角色id列表,格式为1,2,3", paramType = "query", required = true) })
	public String deleteList(@RequestParam String roleIds) {
		roleRepository.delete(roleIds);
		return "批量删除成功";
	}

	@PutMapping("{roleId}")
	@ApiOperation("根据角色id修改角色信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "roleId", value = "角色id", paramType = "path", required = true) })
	public String update(@RequestBody Role role, @PathVariable String roleId) {
		role.setId(roleId);
		role.setModifyDate(new Date());
		roleRepository.save(role);
		return "修改成功";
	}

	@GetMapping("/all")
	@ApiOperation("查询所有角色列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public List<Role> findList() {
		return roleRepository.findAll();
	}

	@GetMapping
	@ApiOperation("分页查询角色列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public Page<Role> findList(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	@GetMapping("/{roleId}")
	@ApiOperation("根据角色id查询角色信息")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public Role findOne(@PathVariable String roleId) {
		return roleRepository.findOne(roleId);
	}

	@GetMapping("/user/{userId}")
	@ApiOperation("根据用户id查询角色列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public List<Role> findListByUserId(@PathVariable String userId) {
		return roleRepository.findByUsers_Id(userId);
	}

}
