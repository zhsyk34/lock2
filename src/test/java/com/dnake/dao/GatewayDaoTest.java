package com.dnake.dao;

import com.dnake.entity.Gateway;
import org.junit.Test;

import java.time.LocalDateTime;

public class GatewayDaoTest extends BaseDaoTest {

	@Test
	public void save() throws Exception {
		for (int i = 0; i < 5; i++) {
			Gateway gateway = new Gateway(null, "sn" + i, "udid" + i, "name" + i, "127.0.0.1", 50000 + i, "192.168.1.110" + i, "v1.10", LocalDateTime.now(), LocalDateTime.now());
			gatewayDao.save(gateway);
		}
	}

	@Test
	public void update() throws Exception {
		Gateway gateway = gatewayDao.findById(2L);
		System.out.println(gateway);
		gateway.setPort(49999);
		gateway.setUpdateTime(LocalDateTime.now());

		gatewayDao.update(gateway);

	}

	@Test
	public void delete() throws Exception {
		System.out.println(gatewayDao.deleteById(1L));
	}

	@Test
	public void delete2() throws Exception {
		System.out.println(gatewayDao.deleteByIds(new Long[]{3L, 7L}));
	}
}