package com.dnake.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dnake.api.CodeEnum;
import com.dnake.api.Feedback;
import com.dnake.api.Info;
import com.dnake.api.Operation;
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
import java.util.List;

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
				case ENTER:
					String r = commandService.enter(udid, info.getName());
					result = r != null;
					if (result) {
						return new Feedback(CodeEnum.SYSTEM.getNumber(), CodeEnum.SYSTEM.getDescription(), r);
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
					return new Feedback(CodeEnum.SYSTEM.getNumber(), CodeEnum.SYSTEM.getDescription(), list);
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
			//入网
			case ENTER:
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
