package com.web.framework.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 启动失败
 * 
 * @author cm_wang
 *
 */
public class ApplicationFailedListener implements ApplicationListener<ApplicationFailedEvent> {
  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void onApplicationEvent(ApplicationFailedEvent event) {
    logger.error("SPRING BOOT STARTING FAILED!!!", event.getException());
    event.getException().printStackTrace();
  }

}
