package com.dnake.entity;

import com.dnake.common.annotation.Id;
import com.dnake.common.annotation.Table;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString

@Table
public class Gateway {
	@Id
	private Long id;
	private String sn;
	private String udid;
	private String name;
	private String ip = "127.0.0.1";
	private int port = 50000;
	private String remote = "114.55.219.171";
	private String version;
	private LocalDateTime createTime = LocalDateTime.now();
	private LocalDateTime updateTime = LocalDateTime.now();
}
