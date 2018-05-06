package ltd.hlmr.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ltd.hlmr.po.Authority;
import ltd.hlmr.po.User;
import ltd.hlmr.repository.AuthorityRepository;
import ltd.hlmr.repository.UserRepository;

/**
 * 重写用户信息获取
 * 
 * @author
 *
 */
@Primary
@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("此用户不存在！");
		} else {
			List<Authority> list = authorityRepository.findByRoles_Users(user);
			Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
			for (Authority e : list) {
				grantedAuthorities.add(new SimpleGrantedAuthority(e.getCode()));
			}
			user.setAuthorities(grantedAuthorities);
			return user;
		}
	}

}