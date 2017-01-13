package com.dnake.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping
public class GuidanceController extends CommonController {

	@RequestMapping(value = "/{module}", method = RequestMethod.GET)
	public String module(@PathVariable("module") String module, HttpServletRequest request) throws IOException {
		if ("logout".equals(module)) {
			request.getSession().invalidate();
			return "redirect:login";
		}
		return module;
	}

}
