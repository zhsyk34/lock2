package com.dnake.dao;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.entity.Lock;

import java.util.List;

public interface LockDao extends CommonDao<Lock, Long> {

	Lock find(String uuid);

	Lock find(long gatewayId, int number, boolean permission);

	List<Lock> findList(long gatewayId, Boolean permission);

	List<Lock> findList(String name, Boolean permission, Sort sort, Page page);

	List<Lock> findList(Long gatewayId, Integer number, String name, Boolean permission, Sort sort, Page page);

	int count(String name, Boolean permission);

}
