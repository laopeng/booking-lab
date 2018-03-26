package ltd.hlmr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ltd.hlmr.po.Authority;
import ltd.hlmr.po.Role;
import ltd.hlmr.po.Student;
import ltd.hlmr.po.User;
import ltd.hlmr.repository.AuthorityRepository;
import ltd.hlmr.repository.RoleRepository;
import ltd.hlmr.repository.StudentRepository;
import ltd.hlmr.repository.UserRepository;
import ltd.hlmr.util.IDGenerator;
import okhttp3.OkHttpClient;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = HlmrRepositoryFactoryBean.class)
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner init() {
		return (args) -> {
			User admin = userRepository.findByUsername("admin");
			if (admin == null) {
				List<Authority> authorities = new ArrayList<>();
				List<Role> roles = new ArrayList<>();
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				authorities.add(
						authorityRepository.save(new Authority(IDGenerator.getId(), "sys_user", "用户管理", new Date())));
				authorities.add(
						authorityRepository.save(new Authority(IDGenerator.getId(), "sys_role", "角色管理", new Date())));
				authorities.add(authorityRepository
						.save(new Authority(IDGenerator.getId(), "sys_authority", "权限管理", new Date())));
				roles.add(roleRepository
						.save(new Role(IDGenerator.getId(), "sys_manager", "系统管理员", new Date(), authorities)));
				userRepository.save(new User(IDGenerator.getId(), "admin", passwordEncoder.encode("admin"), "管理员", "M",
						new Date(), "A", roles));

				String studentId = IDGenerator.getId();
				studentRepository.save(new Student(studentId, "8591", "网络工程", "彭奕", "15622274512", new User(studentId,
						"openid", passwordEncoder.encode("openid"), "测试的学生帐号", "S", new Date(), "A", null)));

				log.debug("Users found with findAll():");
				log.debug("-------------------------------");
				for (User user : userRepository.findAll()) {
					log.debug("此用户的基本信息：" + user.toString());
					log.debug("此用户的角色信息：" + roleRepository.findByUsers_Id(user.getId()));
					log.debug("此用户的权限信息：" + authorityRepository.findByRoles_Users_Id(user.getId()));
				}
				log.debug("------------------------------");
			}
		};
	}

	@Bean
	public OkHttpClient okHttpClient() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(30, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS).retryOnConnectionFailure(true);
		return builder.build();
	}
}
