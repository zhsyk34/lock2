package com.dnake.service;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.entity.Lock;

import java.util.List;

public interface LockService extends CommonService<Lock, Long> {

	Lock find(String uuid);

	Lock findUsed(long gatewayId, int number);

	Lock findUnused(long gatewayId, int number);

	List<Lock> findUsedList(long gatewayId);

	int allocate(long gatewayId);

	List<Lock> findUsedList(String name, Sort sort, Page page);

	int countUsed(String name);

}