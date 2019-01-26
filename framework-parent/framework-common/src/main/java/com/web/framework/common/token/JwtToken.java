package com.web.framework.common.token;

/**
 * JWT(json web token)令牌
 * 
 * @author cm_wang
 */
public class JwtToken extends StatelessToken {

  private static final long serialVersionUID = 1832943548774576547L;

  private String jwt;

  public JwtToken(String host, String jwt) {
    super(host);
    this.jwt = jwt;
  }

  @Override
  public Object getPrincipal() {
    return this.jwt;
  }

  @Override
  public Object getCredentials() {
    return Boolean.TRUE;
  }

  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }
}
