package com.dnake.service;

import com.dnake.exception.ControlException;

//lock-uuid == gateway-udid + number
public interface CommandService {

	/**
	 * 进入对码
	 *
	 * @param udid 网关udid
	 * @param name 入网设备名称
	 * @return 保存设备后产生的编号
	 */
	String enter(String udid, String name);

	//进入绑定
	boolean begin(String udid, int number);

	boolean begin(String uuid);

	//确认绑定
	boolean bind(String udid, int number) throws ControlException;

	boolean bind(String uuid) throws ControlException;

	//修改密码
	boolean word(String udid, int number, int index, String value) throws ControlException;

	boolean word(String uuid, int index, String value) throws ControlException;

	//开锁
	boolean open(String udid, int number);

	boolean open(String uuid);

	//闭锁
	boolean close(String udid, int number);

	boolean close(String uuid);

	//删除密码
	boolean delete(String udid, int number, int index) throws ControlException;

	boolean delete(String uuid, int index) throws ControlException;
}
