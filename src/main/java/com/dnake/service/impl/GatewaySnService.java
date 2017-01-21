package com.dnake.service.impl;

import com.dnake.kit.ConnectionUtil;
import com.xdtech.sh.util.InitServer;

import java.util.HashMap;
import java.util.Map;

public class GatewaySnService {
	private static final String url = InitServer.SN_URL;

	public static String search(String udid) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("querySn", "");
		paramMap.put("udid", udid);
		try {
			return ConnectionUtil.get(url, null, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}