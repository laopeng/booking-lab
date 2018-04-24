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
import org.springframework.transaction.annotation.Transactional;

import ltd.hlmr.po.Authority;
import ltd.hlmr.po.Lab;
import ltd.hlmr.po.Role;
import ltd.hlmr.po.Student;
import ltd.hlmr.po.Teacher;
import ltd.hlmr.po.User;
import ltd.hlmr.repository.AuthorityRepository;
import ltd.hlmr.repository.LabRepository;
import ltd.hlmr.repository.RoleRepository;
import ltd.hlmr.repository.StudentRepository;
import ltd.hlmr.repository.TeacherRepository;
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
	@Autowired
	private LabRepository labRepository;
	@Autowired
	private TeacherRepository teacherRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	@Transactional
	public CommandLineRunner init() {
		return (args) -> {
			User admin = userRepository.findByUsername("admin");
			if (admin == null) {
				/*
				 * 初始化管理员信息
				 */
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
				log.debug("Users found with findAll():");
				log.debug("-------------------------------");
				for (User user : userRepository.findAll()) {
					log.debug("用户的基本信息：" + user.toString());
					log.debug("用户的角色信息：" + roleRepository.findByUsers_Id(user.getId()));
					log.debug("用户的权限信息：" + authorityRepository.findByRoles_Users_Id(user.getId()));
				}
				log.debug("------------------------------");

				/*
				 * 添加测试学生帐号
				 */
				String studentId = IDGenerator.getId();
				studentRepository.save(new Student(studentId, "8591", "网络工程", "彭奕", "15622274512", new User(studentId,
						"openid", passwordEncoder.encode("openid"), "测试的学生帐号", "S", new Date(), "A", null)));
				log.debug("Student found with findAll():");
				log.debug("-------------------------------");
				for (Student student : studentRepository.findAll()) {
					log.debug("测试学生的信息：" + student.toString());
				}
				log.debug("------------------------------");

				/*
				 * 添加实验室
				 */
				String labId = IDGenerator.getId();
				labRepository.save(new Lab(labId, "脑电"));
				labId = IDGenerator.getId();
				labRepository.save(new Lab(labId, "眼动"));
				labId = IDGenerator.getId();
				labRepository.save(new Lab(labId, "生物反馈"));
				labId = IDGenerator.getId();
				labRepository.save(new Lab(labId, "心理测量"));
				log.debug("Lab found with findAll():");
				log.debug("-------------------------------");
				for (Lab lab : labRepository.findAll()) {
					log.debug("实验室的信息：" + lab.toString());
				}
				log.debug("------------------------------");

				/*
				 * 添加指导教师
				 */
				String teacherId = IDGenerator.getId();
				teacherRepository.save(new Teacher(teacherId, "欧进兵", "", "syy@gzucm.edu.cn", new User(teacherId,
						"oujinbing", passwordEncoder.encode("oujinbing"), "指导教师", "S", new Date(), "A", null)));
				teacherId = IDGenerator.getId();
				teacherRepository.save(new Teacher(teacherId, "江雪华", "", "jiangxuehua@gzucm.edu.cn", new User(teacherId,
						"jiangxuehua", passwordEncoder.encode("jiangxuehua"), "指导教师", "S", new Date(), "A", null)));
				teacherId = IDGenerator.getId();
				teacherRepository.save(new Teacher(teacherId, "马利军", "", "malj@gzucm.edu.cn", new User(teacherId,
						"malijun", passwordEncoder.encode("malijun"), "指导教师", "S", new Date(), "A", null)));
				teacherId = IDGenerator.getId();
				teacherRepository.save(new Teacher(teacherId, "陈玉霏", "", "chenyufei@gzucm.edu.cn", new User(teacherId,
						"chenyufei", passwordEncoder.encode("chenyufei"), "指导教师", "S", new Date(), "A", null)));
				teacherId = IDGenerator.getId();
				teacherRepository.save(new Teacher(teacherId, "任滨海", "", "renbinhai@gzucm.edu.cn", new User(teacherId,
						"renbinhai", passwordEncoder.encode("renbinhai"), "指导教师", "S", new Date(), "A", null)));
				teacherId = IDGenerator.getId();
				teacherRepository.save(new Teacher(teacherId, "龚文进", "", "gongwj@gzucm.edu.cn", new User(teacherId,
						"gongwenjin", passwordEncoder.encode("gongwenjin"), "指导教师", "S", new Date(), "A", null)));
				teacherId = IDGenerator.getId();
				teacherRepository.save(new Teacher(teacherId, "图雅", "", "ty@gzucm.edu.cn", new User(teacherId, "tuya",
						passwordEncoder.encode("tuya"), "指导教师", "S", new Date(), "A", null)));
				teacherId = IDGenerator.getId();
				teacherRepository.save(new Teacher(teacherId, "黄时华", "", "hshjg@gzucm.edu.cn", new User(teacherId,
						"huangshihua", passwordEncoder.encode("huangshihua"), "指导教师", "S", new Date(), "A", null)));
				teacherId = IDGenerator.getId();
				teacherRepository.save(new Teacher(teacherId, "李璟", "", "gzylj@gzucm.edu.cn", new User(teacherId,
						"lijing", passwordEncoder.encode("lijing"), "指导教师", "S", new Date(), "A", null)));
				log.debug("Teacher found with findAll():");
				log.debug("-------------------------------");
				for (Teacher teacher : teacherRepository.findAll()) {
					log.debug("指导教师：" + teacher.toString());
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
