package com.dnake.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.dao.GatewayDao;
import com.dnake.dao.LockDao;
import com.dnake.entity.Gateway;
import com.dnake.kit.ValidateKit;
import com.dnake.service.GatewayService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
		String udid = gateway.getUdid();
		Gateway original = gatewayDao.find(udid);
		if (original != null) {
			//网关已存在
			return 0;
		}
		String r = GatewaySnService.search(udid);
		JSONObject json = JSON.parseObject(r);
		if (!json.getBooleanValue("success")) {
			return -1;
		}
		String sn = json.getString("msg");
		if (ValidateKit.empty(sn)) {
			return -1;
		}
		gateway.setSn(sn);
		if (StringUtils.isEmpty(gateway.getVersion())) {
			gateway.setVersion("v1.0");
		}
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
	public Gateway findByUdid(String udid) {
		return gatewayDao.find(udid);
	}

	@Override
	public Gateway findBySn(String sn) {
		List<Gateway> list = gatewayDao.findList(sn, null, null, null);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
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
