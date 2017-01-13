package com.dnake.dao.impl;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.dao.GatewayDao;
import com.dnake.entity.Gateway;
import com.dnake.kit.StringKit;
import com.dnake.kit.ValidateKit;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GatewayDaoImpl extends CommonDaoImpl<Gateway, Long> implements GatewayDao {
	@Override
	public Gateway find(String udid) {
		Map<String, Object> map = new HashMap<>();
		map.put("udid", udid);
		return super.session.selectOne(map);
	}

	@Override
	public List<Gateway> findList(String sn, String name, Sort sort, Page page) {
		return super.findList(restrict(sn, name), sort, page);
	}

	@Override
	public int count(String sn, String name) {
		return super.count(restrict(sn, name));
	}

	private Map<String, Object> restrict(String sn, String name) {
		Map<String, Object> map = new HashMap<>();
		if (ValidateKit.notEmpty(sn)) {
			map.put("sn", StringKit.fuzzy(sn));
		}
		if (ValidateKit.notEmpty(name)) {
			map.put("name", StringKit.fuzzy(name));
		}
		return map;
	}
}
