package ltd.hlmr.repository;

import ltd.hlmr.po.User;

public interface UserRepository extends HlmrRepository<User, String> {
	User findByUsername(String username);
}
