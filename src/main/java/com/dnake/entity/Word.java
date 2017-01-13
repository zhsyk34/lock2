package com.dnake.entity;

import com.dnake.common.annotation.Id;
import com.dnake.common.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@Table
public class Word {
	@Id
	private Long id;
	private Long lockId;
	private int number;
	private String value;
	private LocalDateTime createTime = LocalDateTime.now();
	private LocalDateTime updateTime = LocalDateTime.now();
}
