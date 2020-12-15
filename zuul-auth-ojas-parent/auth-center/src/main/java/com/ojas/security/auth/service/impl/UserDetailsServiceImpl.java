package com.ojas.security.auth.service.impl;

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

import com.ojas.security.auth.dao.RoleDao;
import com.ojas.security.auth.dao.UserDao;
import com.ojas.security.auth.model.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserDao userDAO;
	@Autowired
	private RoleDao roleDAO;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		AppUser appUser = this.userDAO.findUserAccount(userName);
		if (appUser == null) {
			throw new UsernameNotFoundException("User " + userName + " was not found in the database");
		}
		List<String> roleNames = this.roleDAO.getRoleNames(appUser.getUserId());
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNames != null) {
			for (String role : roleNames) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}
		CustomUserDetails user = new CustomUserDetails(appUser.getUserId(), appUser.getEncrytedPassword(), grantList);
		user.setPwdChanged(appUser.isPwdChanged());
		return (UserDetails) user;
	}
}
