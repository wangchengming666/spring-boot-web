package com.web.framework.client;

import org.springframework.http.client.ClientHttpResponse;

public interface HeaderAuthorization {

  /**
   * 得到 Authorization
   * 
   * @return
   */
  public String getAuthorization();

  /**
   * 是否需要重新得到 Authorization<br/>
   * 注意性能
   * 
   * @param clientHttpResponse
   * @return 默认返回false
   */
  default boolean needAuthorization(ClientHttpResponse clientHttpResponse) {
    return false;
  }
}
