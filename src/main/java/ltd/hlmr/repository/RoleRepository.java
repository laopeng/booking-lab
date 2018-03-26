package ltd.hlmr.repository;

import java.util.List;

import ltd.hlmr.po.Role;

public interface RoleRepository extends HlmrRepository<Role, String> {

	List<Role> findByUsers_Id(String userId);

}
