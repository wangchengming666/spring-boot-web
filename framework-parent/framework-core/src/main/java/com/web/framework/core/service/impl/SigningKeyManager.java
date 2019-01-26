package com.web.framework.core.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import com.web.framework.common.utils.JWTUtil;

@Service
public class SigningKeyManager {

  /**
   * jwt keys<br/>
   * key: issuer<br/>
   * value: signingKey
   */
  private static Map<String, byte[]> jwtKeys = new HashMap<String, byte[]>() {
    {
      put("www.proj186dev.anoah.com", "bb3db8569c7ac5eb555e4c853faaf985".getBytes());
    }
  };

  public byte[] getSigningKey(String issuer) {
    return jwtKeys.get(issuer);
  }

  public byte[] getSigningKeyFromJwt(String jwt) {
    Assert.hasText(jwt);
    String issuer = JWTUtil.getJwsNoSign(jwt).getBody().getIssuer();

    return getSigningKey(issuer);
  }
}
