package ltd.hlmr.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
import ltd.hlmr.po.Authority;
import ltd.hlmr.po.User;
import ltd.hlmr.repository.AuthorityRepository;
import ltd.hlmr.repository.UserRepository;
import ltd.hlmr.util.IDGenerator;

@RestController
@RequestMapping("/sys/authorities")
@Api(tags = "权限管理")
public class AuthorityController {
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private UserRepository userRepository;

	@PostMapping
	@ApiOperation("新增权限")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public String add(@RequestBody Authority authority) {
		authority.setId(IDGenerator.getId());
		authority.setCreateDate(new Date());
		authorityRepository.save(authority);
		return "新增成功";
	}

	@DeleteMapping("/{authorityId}")
	@ApiOperation("根据权限id删除权限")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "authorityId", value = "权限id", paramType = "path", required = true) })
	public String delete(@PathVariable String authorityId) {
		authorityRepository.delete(authorityId);
		return "删除成功";
	}

	@DeleteMapping
	@ApiOperation("根据权限id列表删除权限")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "authorityIds", value = "权限id列表,格式为1,2,3", paramType = "query", required = true) })
	public String deleteList(@RequestParam String authorityIds) {
		String[] ids = authorityIds.split(",");
		authorityRepository.delete(ids);
		return "批量删除成功";
	}

	@PutMapping("/{authorityId}")
	@ApiOperation("根据权限id修改权限信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "authorityId", value = "权限id", paramType = "path", required = true) })
	public String update(@RequestBody Authority authority, @PathVariable String authorityId) {
		authority.setId(authorityId);
		authority.setModifyDate(new Date());
		authorityRepository.save(authority);
		return "修改成功";
	}

	@GetMapping("/all")
	@ApiOperation("查询所有权限列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public List<Authority> findList() {
		return authorityRepository.findAll();
	}

	@GetMapping
	@ApiOperation("分页查询权限列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "page", paramType = "query"),
			@ApiImplicitParam(name = "size", paramType = "query") })
	public Page<Authority> findPage(Pageable pageable) {
		return authorityRepository.findAll(pageable);
	}

	@GetMapping("{authorityId}")
	@ApiOperation("根据权限id查询权限信息")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public Authority findOne(@PathVariable String authorityId) {
		return authorityRepository.findOne(authorityId);
	}

	@GetMapping("/user/current")
	@ApiOperation("根据当前登录用户查询权限列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public List<Authority> findListByUserCurrent(@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		User user = userRepository.findByUsername(username);
		return authorityRepository.findByRoles_Users_Id(user.getId());
	}

	@GetMapping("/user/{userId}")
	@ApiOperation("根据用户id查询权限列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public List<Authority> findListByUserId(@PathVariable String userId) {
		return authorityRepository.findByRoles_Users_Id(userId);
	}

	@GetMapping("/role/{roleId}")
	@ApiOperation("根据角色id查询权限列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public List<Authority> findListByRoleId(@PathVariable String roleId) {
		return authorityRepository.findByRoles_Id(roleId);
	}

}
