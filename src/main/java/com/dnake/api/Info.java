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

//	private String appId;
//	private String appSecret;
//	private String nonceStr;
//	private long timestamp;
//
//	private String sign;
}
