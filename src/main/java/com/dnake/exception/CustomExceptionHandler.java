package com.dnake.exception;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public final class CustomExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.debug(ex.getMessage(), ex);

		ModelAndView result = new ModelAndView();

		FastJsonJsonView view = new FastJsonJsonView();
		Map<String, Object> map = new HashMap<>();
		map.put("success", false);
		map.put("msg", ex.getMessage());
		view.setAttributesMap(map);

		result.setView(view);
		return result;
	}
}
