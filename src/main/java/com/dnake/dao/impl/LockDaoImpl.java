package com.dnake.dao.impl;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.dao.LockDao;
import com.dnake.entity.Lock;
import com.dnake.kit.StringKit;
import com.dnake.kit.ValidateKit;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LockDaoImpl extends CommonDaoImpl<Lock, Long> implements LockDao {

	@Override
	public Lock find(String uuid) {
		return super.session.selectOne(uuid);
	}

	@Override
	public Lock find(long gatewayId, int number, boolean permission) {
		List<Lock> list = this.findList(gatewayId, number, null, permission, null, null);
		return ValidateKit.empty(list) ? null : list.get(0);
	}

	@Override
	public List<Lock> findList(long gatewayId, Boolean permission) {
		return super.findList(restrict(gatewayId, -1, null, permission));
	}

	@Override
	public List<Lock> findList(String name, Boolean permission, Sort sort, Page page) {
		return super.findList(restrict(null, -1, name, permission), sort, page);
	}

	@Override
	public List<Lock> findList(Long gatewayId, Integer number, String name, Boolean permission, Sort sort, Page page) {
		return super.findList(restrict(gatewayId, number, name, permission), sort, page);
	}

	@Override
	public int count(String name, Boolean permission) {
		return super.count(restrict(null, -1, name, permission));
	}

	private Map<String, Object> restrict(Long gatewayId, Integer number, String name, Boolean permission) {
		Map<String, Object> map = new HashMap<>();
		if (ValidateKit.valid(gatewayId)) {
			map.put("gatewayId", gatewayId);
		}
		if (number != null && number > -1) {
			map.put("number", number);
		}
		if (ValidateKit.notEmpty(name)) {
			map.put("name", StringKit.fuzzy(name));
		}
		if (permission != null) {
			map.put("permission", permission);
		}
		return map;
	}
}
