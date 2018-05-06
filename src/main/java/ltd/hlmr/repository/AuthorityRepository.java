package ltd.hlmr.repository;

import java.util.List;
import ltd.hlmr.po.Authority;
import ltd.hlmr.po.User;

public interface AuthorityRepository extends HlmrRepository<Authority, String> {
	/**
	 * 根据用户id查询权限列表
	 *
	 * @param id
	 * @return
	 */
	List<Authority> findByRoles_Users_Id(String id);

	/**
	 * 根据用户查询权限列表
	 *
	 * @param user
	 * @return
	 */
	List<Authority> findByRoles_Users(User user);

	/**
	 * 根据角色id查询权限列表
	 *
	 * @param id
	 * @return
	 */
	List<Authority> findByRoles_Id(String id);
}
