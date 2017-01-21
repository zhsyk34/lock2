package com.xdtech.sh.util;

import com.alibaba.fastjson.JSONObject;
import com.dnake.entity.Gateway;
import com.dnake.entity.Lock;
import com.dnake.service.GatewayService;
import com.dnake.service.LockService;
import com.dnk.smart.communication.monitor.UdpClientsMonitor;
import com.dnk.smart.communication.monitor.UdpInfo;
import com.dnk.smart.communication.tcp.TcpSendHandler;
import com.dnk.smart.communication.vo.TcpCtrl;
import org.springframework.context.ApplicationContext;

public class TransHandler {

	private LockService lockService;

	private GatewayService gatewayService;

	private void init() {
		ApplicationContext context = ApplicationContextUtil.context;
		lockService = context.getBean(LockService.class);
		gatewayService = context.getBean(GatewayService.class);
	}

	private void version(TcpCtrl ask, String udid) {
		JSONObject json = new JSONObject();

		//config
		//todo
		json.put("serverIP", "120.76.234.67");
		json.put("serverPort", 15999);
		json.put("ntpIP0", "202.108.6.95");
		json.put("ntpIP1", "132.163.4.101");
		json.put("ntpPort", 123);
		json.put("weatherURL", "api.thinkpage.cn/v3/weather/now.json?key=AL87IW41QK&location=ip&language=z h-Hans&unit=c");

		json.put("result", "ok");
		json.put("flag", ask.getFlag());
		json.put("udid", udid);

		//gateway info
		json.put("appVersionNo", "v1.0");
		json.put("appVersion", "");
		json.put("appURL", "");

		UdpInfo client = UdpClientsMonitor.clients.get(udid);
		TcpSendHandler.send(client.getSocket(), json.toString());
	}

	// TODO:报警信息
	public void handlerRecord(TcpCtrl ask) {
		init();
		Gateway gateway = gatewayService.findBySn(ask.getDevSN());
		if (gateway == null) {
			return;
		}
		Lock lock = lockService.findUsed(gateway.getId(), ask.getDevNo());
		if (lock == null) {
			return;
		}

		String type = ask.getAction();
		switch (type) {
			case "getVersion":
				version(ask, gateway.getUdid());
				break;
		}
	}

}
