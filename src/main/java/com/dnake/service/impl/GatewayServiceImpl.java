package com.dnake.service.impl;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.dao.GatewayDao;
import com.dnake.dao.LockDao;
import com.dnake.entity.Gateway;
import com.dnake.kit.ValidateKit;
import com.dnake.service.GatewayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GatewayServiceImpl implements GatewayService {
	@Resource
	private GatewayDao gatewayDao;
	@Resource
	private LockDao lockDao;

	@Override
	public int save(Gateway gateway) {
		return gatewayDao.save(gateway);
	}

	@Override
	public int deleteByIds(Long[] ids) {
		for (Long id : ids) {
			if (ValidateKit.notEmpty(lockDao.findList(id, null))) {
				return 0;
			}
		}
		return gatewayDao.deleteByIds(ids);
	}

	@Override
	public int update(Gateway gateway) {
		return gatewayDao.update(gateway);
	}

	@Override
	public Gateway find(long id) {
		return gatewayDao.findById(id);
	}

	@Override
	public Gateway find(String udid) {
		return gatewayDao.find(udid);
	}

	@Override
	public List<Gateway> findList() {
		return gatewayDao.findList();
	}

	@Override
	public List<Gateway> findList(String sn, String name, Sort sort, Page page) {
		return gatewayDao.findList(sn, name, sort, page);
	}

	@Override
	public int count(String sn, String name) {
		return gatewayDao.count(sn, name);
	}

}
