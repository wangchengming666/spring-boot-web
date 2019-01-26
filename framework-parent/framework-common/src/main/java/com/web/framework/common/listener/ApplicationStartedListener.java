package com.web.framework.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * 
 * 
 * @author cm_wang
 *
 */
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {
  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void onApplicationEvent(ApplicationStartedEvent event) {

    logger.debug(String.format("Spring Bean Definition Count %s",
        event.getApplicationContext().getBeanDefinitionCount()));

  }

}
