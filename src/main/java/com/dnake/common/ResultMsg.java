package com.dnake.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor(staticName = "from")
@Getter
@Setter
public class ResultMsg {
	private final boolean result;
	private final Object msg;
}
