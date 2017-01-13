package com.dnake.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor(staticName = "of")
public class PageData<T> {
	private final List<T> rows;
	private final Page page;
	private final int total;
}
