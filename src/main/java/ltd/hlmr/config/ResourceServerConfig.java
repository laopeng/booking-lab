package ltd.hlmr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	public static final String RESOURCE_ID = "lab";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().antMatchers("/**/token*")
				.permitAll()
				.antMatchers(HttpMethod.GET, "/**/*swagger*/**", "/**/health", "/**/api-docs", "/", "/favicon.ico",
						"/**/*.jpg", "/**/*.css", "/**/*.js", "/**/*.png", "/**/**.html")
				.permitAll().antMatchers(HttpMethod.POST, "/students").permitAll()
				.antMatchers("/**/token", "/**/h2-console/**", "/wechat", "/wechat/**.txt").permitAll()
				.antMatchers(HttpMethod.OPTIONS).permitAll().antMatchers("/sys/users/**").hasAnyAuthority("sys_user")
				.antMatchers("/sys/roles/**").hasAnyAuthority("sys_role").antMatchers("/sys/authorities/user/current")
				.authenticated().antMatchers("/sys/authorities/**").hasAnyAuthority("sys_authority").anyRequest()
				.authenticated().and().headers().frameOptions().disable().and().csrf().disable();
	}
}