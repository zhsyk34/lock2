package com.dnake.kit;

import com.alibaba.fastjson.JSONObject;
import com.xdtech.sh.util.InitServer;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class SignKit {

	private static final String APP_SECRET = InitServer.APP_SECRET;
	private static final String APP_ID = InitServer.APP_ID;
	private static final String FIX_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static boolean validate(JSONObject json) {
		String sign = json.getString("sign");

		if (ValidateKit.empty(sign)) {
			return false;
		}

		json.put("appSecret", APP_SECRET);// 校验时添加密钥

		return sign.equals(generateSign(json));
	}

	public static String makeSign(JSONObject json) {
		json.put("appId", APP_ID);
		json.put("appSecret", APP_SECRET);
		json.put("timestamp", "" + System.currentTimeMillis());
		json.put("nonceStr", generateNonceStr(36));
		json.put("sign", generateSign(json));

		json.remove("appSecret");// 不发送
		return json.toString();
	}

	private static String generateSign(JSONObject json) {
		List<String> list = new ArrayList<>(json.keySet());
		Collections.sort(list);

		StringBuilder builder = new StringBuilder();

		list.stream().filter((key) -> !"sign".equals(key)).forEach((key) -> builder.append(json.getString(key)));

		return builder.length() > 0 ? SHA1(builder.toString()) : "";
	}

	private static String SHA1(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(str.getBytes(Charset.forName("UTF-8")));
			return byte2hex(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

	private static String byte2hex(final byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		String temp;

		for (byte b : bytes) {
			temp = (Integer.toHexString(b & 0xFF));
			if (temp.length() == 1) {
				builder.append("0");
			}
			builder.append(temp);
		}

		return builder.toString();
	}

	/**
	 * 生成随机字符串
	 */
	private static String generateNonceStr(int length) {
		final int range = FIX_CHARS.length();
		final Random random = new Random();
		final StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length; i++) {
			builder.append(FIX_CHARS.charAt(Math.abs(random.nextInt()) % range));
		}

		return builder.toString();
	}

}
