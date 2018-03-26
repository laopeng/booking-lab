package ltd.hlmr.controller;

import java.util.Date;
import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
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
import ltd.hlmr.po.User;
import ltd.hlmr.repository.UserRepository;
import ltd.hlmr.util.IDGenerator;

@RestController
@RequestMapping("/sys/users")
@Api(tags = "用户管理")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@PostMapping
	@ApiOperation("新增用户")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public String add(@RequestBody User user) {
		user.setId(IDGenerator.getId());
		user.setCreateDate(new Date());
		if (StringUtils.hasText(user.getPassword())) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		} else {
			throw new ServiceException("密码不能为空");
		}
		userRepository.save(user);
		return "新增成功";
	}

	@DeleteMapping("/{userId}")
	@ApiOperation("根据用户id删除用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true) })
	public String delete(@PathVariable String userId) {
		userRepository.delete(userId);
		return "删除成功";
	}

	@DeleteMapping
	@ApiOperation("根据用户id列表删除用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "userIds", value = "用户id列表，格式为1,2,3", paramType = "query", required = true) })
	public String deleteList(@RequestParam String userIds) {
		String[] ids = userIds.split(",");
		userRepository.delete(ids);
		return "批量删除成功";
	}

	@PutMapping("/{userId}")
	@ApiOperation("根据用户id修改用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer "),
			@ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true) })
	public String update(@RequestBody User user, @PathVariable String userId) {
		User oldUser = userRepository.findOne(userId);
		if (oldUser != null) {
			user.setId(userId);
			if (StringUtils.hasText(user.getPassword())) {
				String oldPassword = oldUser.getPassword();
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String newPassword = passwordEncoder.encode(user.getPassword());
				if (!oldPassword.equals(newPassword)) {// 新旧密码不一致，则更新密码最后更新时间，用作密码修改token跟着过期操作
					user.setLastPasswordResetDate(new Date());
				}
				user.setPassword(newPassword);
			} else {// 设为空值不更新密码
				user.setPassword(oldUser.getPassword());
			}
			user.setModifyDate(new Date());
			userRepository.save(user);
		}
		return "更新成功";
	}

	@GetMapping("/all")
	@ApiOperation("查询所有用户列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public List<User> findList() {
		return userRepository.findAll();
	}

	@GetMapping
	@ApiOperation("分页查询用户列表")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public Page<User> findList(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@GetMapping("{userId}")
	@ApiOperation("根据用户id查询用户信息")
	@ApiImplicitParam(name = "Authorization", value = "Bearer token", paramType = "header", required = true, defaultValue = "Bearer ")
	public User findOne(@PathVariable String userId) {
		return userRepository.findOne(userId);
	}

}
