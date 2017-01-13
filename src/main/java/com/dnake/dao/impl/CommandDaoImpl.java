package com.dnake.dao.impl;

import com.dnake.dao.CommandDao;
import com.dnake.kit.ValidateKit;
import com.dnk.smart.communication.monitor.UdpClientsMonitor;
import com.dnk.smart.communication.monitor.UdpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

//TODO:TEST
@Repository
public class CommandDaoImpl implements CommandDao {

	private static final int AREA = -1;// 默认区域号,兼容用
	private static final String TYPE = "mlock";// 默认锁类型,兼容用
	private final Logger logger = LoggerFactory.getLogger(CommandDaoImpl.class);

	private UdpInfo info(String udid) {
		if (ValidateKit.empty(udid)) {
			return null;
		}

		UdpInfo client = UdpClientsMonitor.clients.get(udid);
		if (client != null) {
			return client;
		}
		logger.info("网关下线,发送登录提醒");

		int count = 0;
		while (client == null && count < 46) {
			client = UdpClientsMonitor.getClientByUdid(udid);
			count++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return client;
	}

	//对码
	private boolean ready(String udid, boolean active) {
		UdpInfo info = info(udid);
//		return info != null && TcpSendHandler.checkControl(info, active ? 1 : 0);
		return true;//todo
	}

	//控制
	private boolean ctrl(String udid, int number, boolean open) {
		UdpInfo info = info(udid);
//		return info != null && TcpSendHandler.controlDoorLock(info, AREA, number, open ? 1 : 0, TYPE);
		return true;//todo
	}

	@Override
	public boolean enter(String udid) {
		return this.ready(udid, true);
	}

	@Override
	public boolean exit(String udid) {
		return this.ready(udid, false);
	}

	@Override
	public boolean open(String udid, int number) {
		return this.ctrl(udid, number, true);
	}

	@Override
	public boolean close(String udid, int number) {
		return this.ctrl(udid, number, false);
	}

	@Override
	public boolean word(String udid, int number, int index, String value) {
//		UdpInfo info = info(udid);
//		if (info == null) {
//			return false;
//		}
//
//		switch (index) {
//			case 1://manager
//				return TcpSendHandler.setPwd(info, number, 1, value, 1, null);
//			case 99://temp
//				return TcpSendHandler.setTempPwd(info, number, value, 0);
//			default://user
//				return TcpSendHandler.setPwd(info, number, index, value, 1, null);
//		}
		return true;//todo
	}

	@Override
	public boolean delete(String udid, int number, int index) {
//		if (number < 0 || number > 100 || index <= 1 || index >= 99) {
//			return false;
//		}
//		UdpInfo info = info(udid);
//		return info != null && TcpSendHandler.delPwd(info, number, index);
		return true;//todo
	}
}
