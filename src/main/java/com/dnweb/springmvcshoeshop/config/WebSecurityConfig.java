package com.dnweb.springmvcshoeshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.dnweb.springmvcshoeshop.authentication.MyDBAuthenticationService;
import com.dnweb.springmvcshoeshop.handler.AfterLoginSuccessHandler;

@Configuration

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	MyDBAuthenticationService myDBAauthenticationService;
	
	@Autowired
	private AfterLoginSuccessHandler successHandler;

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
		http.authorizeRequests().antMatchers("/orderList", "/order", "/profile")//
				.access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')"); // Cho nay!!

		// Trang chỉ dành cho ADMIN
		http.authorizeRequests().antMatchers("/product").access("hasRole('ROLE_ADMIN')");

		// Khi người dùng đã login, với vai trò XX.
		// Nhưng truy cập vào trang yêu cầu vai trò YY,
		// Ngoại lệ AccessDeniedException sẽ ném ra.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		
		System.out.println(">>>>> successHandler= "+successHandler);

		 
		// Cấu hình cho Login Form.
		http.authorizeRequests().and().formLogin()//
		        
				// Submit URL của trang login
				.loginProcessingUrl("/j_spring_security_check") // Submit URL
				.loginPage("/login")//
				.defaultSuccessUrl("/profile")// Nhu cau hinh o day!! (Cai nay mat tac dung neu co SuccessHander)
				.failureUrl("/login?error=true")//
				.usernameParameter("userName")//
				.passwordParameter("password") 
				// Dang ky bo dieu khien (handler) khi login thanh cong
         		// Nhu vay khi Login thanh cong ==> SuccessHnder dc goi ==> Ghi thong tin vao session!
				// Mac dinh neu khong dang ky Handler ==> Sau khi login thanh cong no nhay toi /profile
                .successHandler(successHandler)
				//Cấu hình cho signup page
				

				// Cấu hình cho Logout Page.
				// (Sau khi logout, chuyển tới trang home)
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}

}
