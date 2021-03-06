package com.dnake.controller;

import com.dnake.common.Order;
import com.dnake.common.Sort;
import com.dnake.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class CommonController {

	protected static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	static final Sort SORT = Sort.of("id", Order.DESC);

	@Resource
	UserService userService;
	@Resource
	GatewayService gatewayService;
	@Resource
	LockService lockService;
	@Resource
	WordService wordService;
	@Resource
	CommandService commandService;

}
