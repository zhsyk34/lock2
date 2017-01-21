package com.xdtech.sh.util;

import com.dnk.smart.communication.monitor.UdpClientsMonitor;
import com.dnk.smart.communication.tcp.TcpServer;
import com.dnk.smart.communication.udp.UdpServer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Component
public class InitServer {

	public static final String APP_ID;
	public static final String APP_SECRET;
	public static String SN_URL;
	public static String DEFAULT_PASSWORD;
	private static String TCP_PORT;
	private static String UDP_PORT;

	static {
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(InitServer.class.getResourceAsStream("/config.properties"), StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
		SN_URL = properties.getProperty("snurl");
		DEFAULT_PASSWORD = properties.getProperty("defpwd");

		TCP_PORT = properties.getProperty("tcpport");
		UDP_PORT = properties.getProperty("udpport");

		APP_ID = properties.getProperty("appid");
		APP_SECRET = properties.getProperty("appsecret");
	}

	@PostConstruct
	public void init() {
		UdpClientsMonitor monitor = new UdpClientsMonitor();
		monitor.initialize();
		UdpServer.initUdpServer(UDP_PORT, true);
		TcpServer.initTcpServer(Integer.valueOf(TCP_PORT), 10000);
	}

}
