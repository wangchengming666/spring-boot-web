package com.web.framework.common.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class RuntimeUtil {
  /**
   * 得到当前进程ID
   * 
   * @return
   */
  public static final String getProcessID() {
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    return runtimeMXBean.getName().split("@")[0];
  }
}
