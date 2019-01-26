package com.web.framework.common.constants;

/**
 * 开发，测试，生产环境配置
 * 
 * @author cm_wang
 *
 */
public class ActiveProfilesConstants {

  public static boolean isPrd = true;
  public static boolean isDev = true;

  /**
   * 是否是生产环境
   * 
   * @return
   */
  public static boolean isPrd() {

    return isPrd;
  }


  /**
   * 是否是生产环境
   * 
   * @return
   */
  public static boolean isDev() {

    return isDev;
  }

}
