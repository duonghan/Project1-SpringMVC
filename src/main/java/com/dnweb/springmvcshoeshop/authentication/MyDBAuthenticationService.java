package com.dnweb.springmvcshoeshop.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dnweb.springmvcshoeshop.dao.AccountDAO;
import com.dnweb.springmvcshoeshop.entities.Account;

@Service
public class MyDBAuthenticationService implements UserDetailsService {

	@Autowired
	private AccountDAO accountDAO;
	
	// Dich vu nay khong duoc goi!!
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Account account = accountDAO.findAccountByUsername(username);
		
		System.out.println("Account= " + account);

		if (account == null) {
			throw new UsernameNotFoundException("User " //
					+ username + " was not found in the database");
		}

		// ADMIN, USER
		String role = account.getRole();

		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		// ROLE_ADMIN, ROLE_USER
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
		grantList.add(authority);

		// ??
		//may cai tham so duoi la mac dinh a a
		//e thay trong bai login k co
		// Thuc ra vi du Login bang Account khong co 
		// may truong nonExpiri (Khong bao gio het han)
		/// credentialsNonExpired: Giay phep khong bao go het han
		// Tai khoan ko bi khoa,...
		// Spring nos dưa ra một quy tắc Tổng quát nhát (Van de ứng dụng cua minh ko 
		// Sử dụng tới.
		boolean enabled = account.isActive();
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		UserDetails userDetails = (UserDetails) new User(account.getUsername(), //
				account.getPassword(), enabled, accountNonExpired, //
				credentialsNonExpired, accountNonLocked, grantList);

		return userDetails;
	}

}
