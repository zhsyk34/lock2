package com.dnake.dao;

import com.dnake.common.Order;
import com.dnake.common.Sort;
import com.dnake.entity.Lock;
import org.junit.Test;

public class LockDaoTest extends BaseDaoTest {

	@Test
	public void save() throws Exception {
		for (int i = 0; i < 3; i++) {
			Lock lock = new Lock().setGatewayId((long) (i % 2) + 1).setName("n" + i).setNumber(i + 23);
			lockDao.save(lock);
		}

	}

	@Test
	public void find() throws Exception {
		System.out.println(lockDao.find(1L, 1, false));
	}

	@Test
	public void find1() throws Exception {
		System.out.println(lockDao.find("dd7abc58-d86b-11e6-aa5c-0a0027000004"));
	}

	@Test
	public void findList() throws Exception {
		lockDao.findList(1L, true).forEach(System.out::println);
	}

	@Test
	public void findList1() throws Exception {
		lockDao.findList("n", null, Sort.of("id", Order.ASC), null).forEach(System.out::println);
	}

	@Test
	public void findList2() throws Exception {
	}

	@Test
	public void count() throws Exception {
		System.out.println(lockDao.count("n", null));
	}

	@Test
	public void del() throws Exception {
		System.out.println(wordDao.deleteByLock(2L));
	}
}