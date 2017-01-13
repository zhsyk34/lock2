package com.dnake.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Info {
	private String gateway;
	private String name;

	private String lock;

	private String password;
	private int index;

	private String appId;
	private String appSecret;
	private String nonceStr;
	private long timestamp;

	private String sign;
}
/**
 * Map<String, String> map = new HashMap<>();
 * map.put("password", "666666");//设置密码
 * map.put("index", "5");
 * map.put("appId", appId);
 * map.put("lockId", lockId);
 * map.put("nonceStr", nonceStr);
 * map.put("appSecret", appSecret);
 * String timestamp = Long.toString(new Date().getTime());
 * map.put("timestamp", timestamp);
 * <p>
 * String sign = generateSign(map);
 * map.put("sign", sign);
 * map.remove("appSecret");
 */
