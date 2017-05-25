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
