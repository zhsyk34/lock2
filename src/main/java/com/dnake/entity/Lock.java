package com.dnake.entity;

import com.dnake.common.annotation.Id;
import com.dnake.common.annotation.Table;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table("locks")
public class Lock {
	@Id
	private Long id;
	private Long gatewayId;
	private int number;
	private String uuid;
	private String name;
	private boolean permission = false;
	private LocalDateTime createTime = LocalDateTime.now();
	private LocalDateTime updateTime = LocalDateTime.now();
}
