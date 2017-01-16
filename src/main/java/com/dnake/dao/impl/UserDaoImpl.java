package com.dnake.dao.impl;

import com.dnake.dao.UserDao;
import com.dnake.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDaoImpl extends CommonDaoImpl<User, Long> implements UserDao {
	@Override
	public User findByName(String name) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		return super.session.selectOne(map);
	}
}
