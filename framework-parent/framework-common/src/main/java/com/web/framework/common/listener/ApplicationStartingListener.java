package com.web.framework.common.listener;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import com.web.framework.common.utils.RuntimeUtil;

public class ApplicationStartingListener implements ApplicationListener<ApplicationStartingEvent> {
  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void onApplicationEvent(ApplicationStartingEvent event) {

    event.getSpringApplication().setBannerMode(Banner.Mode.OFF);

    setPidFile();

  }

  /**
   * 设置pid文件
   */
  private void setPidFile() {
    try {
      FileUtils.writeByteArrayToFile(new File("pid"), RuntimeUtil.getProcessID().getBytes());
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }
}
