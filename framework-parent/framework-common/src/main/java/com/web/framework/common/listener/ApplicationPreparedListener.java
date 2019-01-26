package com.web.framework.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * 
 * @author cm_wang
 *
 */
public class ApplicationPreparedListener implements ApplicationListener<ApplicationPreparedEvent> {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ApplicationPreparedEvent event) {
	}

}
