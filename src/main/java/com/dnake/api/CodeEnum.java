package com.dnake.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CodeEnum {
	NULL(0, "成功"),
	COMMON(10001, "通用错误"),
	SYSTEM(1002, "系统错误"),
	PARAM(1003, "参数错误"),
	SIGN(1004, "签名错误");

	private final int number;
	private final String description;
}
