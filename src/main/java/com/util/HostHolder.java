package com.util;

import com.dao.UserDao;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 获取当前登录的用户User
 *
 */
@Component
public class HostHolder {
	
	private User user;
	
	@Autowired
    UserDao userDao;
	
	public User getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user=userDao.getUserByUsername(((UserDetails) principal).getUsername());
		return user;
	}
}
