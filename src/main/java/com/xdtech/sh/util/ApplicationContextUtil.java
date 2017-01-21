package com.xdtech.sh.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {
	static ApplicationContext context;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		ApplicationContextUtil.context = context;
	}
}
