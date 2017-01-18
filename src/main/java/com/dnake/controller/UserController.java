package com.dnake.controller;

import com.dnake.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController extends CommonController {

	private static final String LOGIN_SESSION_KEY = "user";

	@RequestMapping(value = "/login")
	@ResponseBody
	public boolean login(String name, String password, HttpServletRequest request) {
		User user = userService.login(name, password);
		if (user != null) {
			request.getSession().setAttribute(LOGIN_SESSION_KEY, user);
		}
		return user != null;
	}
}
