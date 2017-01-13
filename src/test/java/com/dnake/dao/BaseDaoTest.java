package com.dnake.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/spring.xml"})
public class BaseDaoTest {

	@Resource
	protected GatewayDao gatewayDao;
	@Resource
	protected LockDao lockDao;
	@Resource
	protected WordDao wordDao;
}
