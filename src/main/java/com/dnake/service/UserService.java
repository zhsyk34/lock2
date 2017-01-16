package com.dnake.service;

import com.dnake.entity.User;

public interface UserService {

	User login(String name, String password);

}