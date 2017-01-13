package com.dnake.dao;

import com.dnake.common.Page;
import com.dnake.common.Sort;
import com.dnake.entity.Gateway;

import java.util.List;

public interface GatewayDao extends CommonDao<Gateway, Long> {

	Gateway find(String udid);

	List<Gateway> findList(String sn, String name, Sort sort, Page page);

	int count(String sn, String name);
}
