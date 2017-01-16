package com.dnake.dao;

import com.dnake.entity.User;

public interface UserDao extends CommonDao<User, Long> {
	User findByName(String name);
}
