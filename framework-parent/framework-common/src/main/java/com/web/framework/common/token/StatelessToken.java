package com.web.framework.common.token;

import java.time.LocalTime;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 无状态令牌抽象
 * 
 * @author cm_wang
 */
public abstract class StatelessToken implements AuthenticationToken {

  private static final long serialVersionUID = 6655946030026745372L;

  private String host;// 客户IP
  private LocalTime localTime; // 时间

  public StatelessToken(String host) {
    this.host = host;
    this.localTime = LocalTime.now();
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public LocalTime getLocalTime() {
    return localTime;
  }

  public void setLocalTime(LocalTime localTime) {
    this.localTime = localTime;
  }

}
