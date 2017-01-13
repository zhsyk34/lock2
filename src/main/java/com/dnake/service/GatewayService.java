package com.dnake.service;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.entity.Gateway;

import java.util.List;

public interface GatewayService {

	int save(Gateway gateway);

	int deleteByIds(Long[] ids);

	int update(Gateway gateway);

	Gateway find(long id);

	Gateway find(String udid);

	List<Gateway> findList();

	List<Gateway> findList(String sn, String name, Sort sort, Page page);

	int count(String sn, String name);
}