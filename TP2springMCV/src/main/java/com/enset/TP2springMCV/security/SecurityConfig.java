package com.enset.TP2springMCV.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = passwordEncoder();
		 // JDBC authentication
		 
		 
		 auth.jdbcAuthentication().dataSource(dataSource)
		 .usersByUsernameQuery("select username as principal, password as credentials, active from users where username=?")
		 .authoritiesByUsernameQuery("select username as principal, role as role from users_roles where username =?")
		 .passwordEncoder(passwordEncoder).rolePrefix("ROLE_");
		 
		
		
		System.out.println("==============================");
		 System.out.println(passwordEncoder.encode("1234"));
		 System.out.println("==============================");
		
		// in memory authentication
		 /*
		  auth.inMemoryAuthentication().withUser("user1").password(passwordEncoder
				  .encode("1234")).roles("USER");
		  auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.
		  encode("1234")).roles("USER");
		  auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder.
		  encode("1234")).roles("USER","ADMIN");
		  */
		 
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/save**/**","/form**/**", 
				"/delete**/**","update**/**").hasRole("ADMIN");
	    //http.authorizeRequests().antMatchers("/patients**/**").hasRole("USER");
	     // http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/login","/webjars/**").permitAll();
		http.authorizeRequests().anyRequest().authenticated();
		//http.csrf();
		
		http.exceptionHandling().accessDeniedPage("/notAuthorized");
	
	}

}
