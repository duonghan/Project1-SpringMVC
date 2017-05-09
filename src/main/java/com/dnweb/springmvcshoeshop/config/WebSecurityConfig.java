package com.dnweb.springmvcshoeshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.dnweb.springmvcshoeshop.authentication.MyDBAuthenticationService;

@Configuration

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	MyDBAuthenticationService myDBAauthenticationService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Các User trong Database
		auth.userDetailsService(myDBAauthenticationService);

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		// Các trang không yêu cầu login
		http.authorizeRequests().antMatchers("/", "/welcome", "/login", "/logout","/signup").permitAll();

		// Các yêu cầu phải login với vai trò USER hoặc ADMIN.
		// Nếu chưa login, nó sẽ redirect tới trang /login.
		http.authorizeRequests().antMatchers("/orderList", "/order", "/accountInfo")//
				.access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')"); // Cho nay!!

		// Trang chỉ dành cho ADMIN
		http.authorizeRequests().antMatchers("/product").access("hasRole('ROLE_ADMIN')");

		// Khi người dùng đã login, với vai trò XX.
		// Nhưng truy cập vào trang yêu cầu vai trò YY,
		// Ngoại lệ AccessDeniedException sẽ ném ra.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Cấu hình cho Login Form.
		http.authorizeRequests().and().formLogin()//

				// Submit URL của trang login
				.loginProcessingUrl("/j_spring_security_check") // Submit URL
				.loginPage("/login")//
				.defaultSuccessUrl("/accountInfo")//
				.failureUrl("/login?error=true")//
				.usernameParameter("userName")//
				.passwordParameter("password")
				
				//Cấu hình cho signup page
				

				// Cấu hình cho Logout Page.
				// (Sau khi logout, chuyển tới trang home)
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}

}
