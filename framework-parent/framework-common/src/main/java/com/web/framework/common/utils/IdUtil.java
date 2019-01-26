package com.web.framework.common.utils;

import com.web.framework.common.generator.SnowFlakeGenerator;

public class IdUtil {
  private static SnowFlakeGenerator idWorker = new SnowFlakeGenerator(1, 1);

  public static Long nextId() {
    return idWorker.nextId();
  }

}
