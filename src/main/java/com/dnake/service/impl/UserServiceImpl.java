package com.dnake.service.impl;

import com.dnake.dao.UserDao;
import com.dnake.entity.User;
import com.dnake.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;

	@Override
	public User login(String name, String password) {
		User user = userDao.findByName(name);
		if (user == null) {
			return null;
		}
		return user.getPassword().equals(password) ? user : null;
	}
}
