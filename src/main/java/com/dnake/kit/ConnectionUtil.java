package com.dnake.kit;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

enum Method {
	GET, POST
}

public class ConnectionUtil {

	private static final String CHARSET = "UTF-8";

	public static String get(String url, Map<String, String> headerMap, Map<String, String> paramMap) throws Exception {
		if (paramMap != null && !paramMap.isEmpty()) {
			url += parseParams(Method.GET, paramMap);
		}

		URL address = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) address.openConnection();

		setHeader(connection, headerMap, true);

		int code = connection.getResponseCode();
		if (code != 200) {
			return receive(connection.getErrorStream());
		}

		String result = receive(connection.getInputStream());
		connection.disconnect();

		return result;
	}

	public static String post(String url, Map<String, String> headerMap, Map<String, String> paramMap) throws Exception {
		URL address = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) address.openConnection();

		connection.setRequestMethod(Method.POST.toString());
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);

		setHeader(connection, headerMap, true);

		if (paramMap != null && !paramMap.isEmpty()) {
			PrintWriter out;
			out = new PrintWriter(connection.getOutputStream());
			out.print(parseParams(Method.POST, paramMap));
			out.flush();
			out.close();
		}

		int code = connection.getResponseCode();
		if (code != 200) {
			return receive(connection.getErrorStream());
		}

		String result = receive(connection.getInputStream());
		connection.disconnect();
		return result;
	}

	// 设置常用首部
	public static Map<String, String> setBaseHeader() {
		Map<String, String> map = new HashMap<>();
		map.put("Accept", "*/*");
		map.put("Accept-Charset", CHARSET);

		map.put("Cache-Control", "no-cache");
		map.put("Pragma", "no-cache");

		map.put("Connection", "keep-alive");
		map.put("Content-Type", "application/x-www-form-urlencoded; charset=" + CHARSET);
		return map;
	}

	// base:是否设置默认值
	public static void setHeader(URLConnection connection, Map<String, String> map, boolean base) {
		if (map == null) {
			if (base) {
				map = new HashMap<>();
				map.putAll(setBaseHeader());
			} else {
				return;
			}
		}
		map.forEach((k, v) -> connection.setRequestProperty(k, v));
	}

	// 请求参数处理
	public static String parseParams(Method method, Map<String, String> params) {
		StringBuilder result = new StringBuilder();
		params.forEach((k, v) -> result.append("&" + k + "=" + v));
		result.replace(0, 1, "");

		switch (method) {
			case GET:
				result.insert(0, "?");
				break;
			case POST:
				break;
		}
		return result.toString();
	}

	// 将得到的inputStream转为string
	public static String receive(InputStream in) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		reader.close();
		return builder.toString();
	}
}