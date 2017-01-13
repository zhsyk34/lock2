package com.dnake.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum Operation {
	ENTER("入网"),
	BEGIN("对码"),
	BIND("绑定"),
	OPEN("开锁"),
	WORD("保存/修改密码"),
	DELETE("删除密码"),
	QUERY("查询密码");
	private static final Map<String, Operation> MAP;

	static {
		MAP = new HashMap<>();
		for (Operation operation : values()) {
			MAP.put(operation.name().toLowerCase(), operation);
		}
	}

	private final String description;

	public static Operation from(String type) {
		return MAP.get(type);
	}
}
