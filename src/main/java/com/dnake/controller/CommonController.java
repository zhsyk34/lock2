package com.dnake.controller;

import com.dnake.common.Order;
import com.dnake.common.Sort;
import com.dnake.service.CommandService;
import com.dnake.service.GatewayService;
import com.dnake.service.LockService;
import com.dnake.service.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class CommonController {

	protected static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	static final Sort SORT = Sort.of("id", Order.DESC);

	@Resource
	GatewayService gatewayService;
	@Resource
	LockService lockService;
	@Resource
	WordService wordService;
	@Resource
	CommandService commandService;

}
