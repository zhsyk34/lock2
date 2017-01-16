package com.dnake.service.impl;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.dao.LockDao;
import com.dnake.dao.WordDao;
import com.dnake.entity.Lock;
import com.dnake.kit.ValidateKit;
import com.dnake.service.LockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LockServiceImpl extends CommonServiceImpl<Lock, Long> implements LockService {
	@Resource
	private LockDao lockDao;
	@Resource
	private WordDao wordDao;

	@Override
	public Lock find(String uuid) {
		return lockDao.find(uuid);
	}

	@Override
	public Lock findUsed(long gatewayId, int number) {
		return lockDao.find(gatewayId, number, true);
	}

	@Override
	public Lock findUnused(long gatewayId, int number) {
		return lockDao.find(gatewayId, number, false);
	}

	@Override
	public List<Lock> findUsedList(long gatewayId) {
		return lockDao.findList(gatewayId, true);
	}

	@Override
	public int allocate(long gatewayId) {
		List<Lock> list = this.findUsedList(gatewayId);
		if (ValidateKit.empty(list)) {
			return 0;
		}

		List<Integer> ids = list.stream().map(Lock::getNumber).collect(Collectors.toList());
		ids.forEach(System.out::println);
		for (int i = 0; i < 100; i++) {
			if (!ids.contains(i)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public List<Lock> findUsedList(String name, Sort sort, Page page) {
		return lockDao.findList(name, true, sort, page);
	}

	@Override
	public int countUsed(String name) {
		return lockDao.count(name, true);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		for (long id : ids) {
			wordDao.deleteByLock(id);
		}
		lockDao.deleteByIds(ids);
		return ids.length;
	}

}
