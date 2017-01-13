package com.dnake.dao;

public interface CommandDao {

	//进入对码
	boolean enter(String udid);

	//退出对码
	boolean exit(String udid);

	//开锁
	boolean open(String udid, int number);

	//闭锁
	boolean close(String udid, int number);

	//设置密码
	boolean word(String udid, int number, int index, String value);

	//删除密码
	boolean delete(String udid, int number, int index);
}
