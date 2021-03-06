package com.dnake.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dnake.api.CodeEnum;
import com.dnake.api.Feedback;
import com.dnake.api.Info;
import com.dnake.api.Operation;
import com.dnake.entity.Gateway;
import com.dnake.entity.Lock;
import com.dnake.entity.Word;
import com.dnake.exception.ControlException;
import com.dnake.kit.SignKit;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class RemoteController extends CommonController {

	// 数据处理
	private Feedback operation(@NonNull Operation operation, @NonNull Info info) {
		String uuid = info.getLock();
		String udid = info.getGateway();

		boolean result = false;
		logger.debug("远程请求{}:{}", operation, info);

		try {
			switch (operation) {
				case REGISTER:
					Gateway gateway = new Gateway();
					gateway.setUdid(udid).setName(info.getName());
					int save = gatewayService.save(gateway);
					switch (save) {
						case 0:
							return new Feedback(CodeEnum.PARAM.getNumber(), CodeEnum.PARAM.getDescription(), "该网关已入网");
						case -1:
							return new Feedback(CodeEnum.SYSTEM.getNumber(), CodeEnum.SYSTEM.getDescription(), "系统错误");
						default:
							result = true;
					}
					break;
				case ENTER:
					String r = commandService.enter(udid, info.getName());
					result = (r != null);
					if (result) {
						return new Feedback(CodeEnum.NULL.getNumber(), CodeEnum.NULL.getDescription(), r);
					}
					break;
				case BEGIN:
					result = commandService.begin(uuid);
					break;
				case BIND:
					result = commandService.bind(uuid);
					break;
				case OPEN:
					result = commandService.open(uuid);
					break;
				case WORD:
					result = commandService.word(uuid, info.getIndex(), info.getPassword());
					break;
				case DELETE:
					result = commandService.delete(uuid, info.getIndex());
					break;
				case QUERY:
					Lock lock = lockService.find(uuid);
					if (lock == null) {
						return new Feedback(CodeEnum.PARAM.getNumber(), CodeEnum.PARAM.getDescription(), null);
					}
					List<Word> list = wordService.findList(lock.getId());
					List<Map<String, Object>> maps = new ArrayList<>();
					if (list != null) {
						list.forEach(word -> {
							Map<String, Object> map = new HashMap<>();
							map.put("index", word.getNumber());
							map.put("password", word.getValue());
							maps.add(map);
						});
					}
					return new Feedback(CodeEnum.SYSTEM.getNumber(), CodeEnum.SYSTEM.getDescription(), maps);
				default:
					break;
			}
		} catch (ControlException e) {
			e.printStackTrace();
		}

		if (result) {
			return new Feedback(CodeEnum.NULL.getNumber(), CodeEnum.NULL.getDescription(), operation.getDescription() + "成功");
		}
		return new Feedback(CodeEnum.SYSTEM.getNumber(), CodeEnum.SYSTEM.getDescription(), operation.getDescription() + "失败");
	}

	@RequestMapping(value = "/{type}")
	@ResponseBody
	public String handler(HttpServletRequest request, @PathVariable("type") String type) {
		Operation operation = Operation.from(type);
		JSONObject params = null;
		try {
			params = JSON.parseObject(request.getInputStream(), JSONObject.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Feedback feedback = before(operation, params);

		JSONObject json = (JSONObject) JSON.toJSON(feedback);
		System.out.println(json);
		return SignKit.makeSign(json);
	}

	public Feedback before(Operation operation, JSONObject params) {
		if (operation == null) {
			return new Feedback(CodeEnum.COMMON.getNumber(), "请求地址错误", null);
		}
		if (params == null || params.isEmpty()) {
			return new Feedback(CodeEnum.PARAM.getNumber(), CodeEnum.PARAM.getDescription(), null);
		}
		if (!SignKit.validate(params)) {
			return new Feedback(CodeEnum.SIGN.getNumber(), CodeEnum.SIGN.getDescription(), null);
		}

		Info info = JSON.parseObject(params.toJSONString(), Info.class);
		if (info == null || !validate(operation, info)) {
			return new Feedback(CodeEnum.PARAM.getNumber(), CodeEnum.PARAM.getDescription(), null);
		}

		return operation(operation, info);
	}

	private boolean validate(@NonNull Operation operation, @NonNull Info info) {
		String uuid = info.getLock();
		int index = info.getIndex();
		switch (operation) {
			case REGISTER://网关入网
			case ENTER://门锁入网
				return StringUtils.hasText(info.getGateway()) && StringUtils.hasText(info.getName());
			case BEGIN:
			case BIND:
			case OPEN:
			case QUERY:
				return StringUtils.hasText(uuid);
			case WORD:
				return StringUtils.hasText(uuid) && StringUtils.hasText(info.getPassword()) && index > 0 && index < 100;
			case DELETE:
				return StringUtils.hasText(uuid) && index > 0 && index < 100;
			default:
				return false;
		}
	}

}
