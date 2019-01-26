package com.web.framework.core.controller.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.constants.KeysConstants;
import com.web.framework.common.generator.JwtRecreateGenerator;
import com.web.framework.common.listener.CreateUserListener;
import com.web.framework.common.utils.CookieUtil;
import com.web.framework.common.utils.JWTUtil;
import com.web.framework.common.utils.R;
import com.web.framework.common.utils.S;
import com.web.framework.core.base.ControllerBase;
import com.web.framework.core.entity.UcUser;
import com.web.framework.core.service.UcUserService;
import com.web.framework.core.service.impl.SigningKeyManager;

/**
 * 
 * @author cm_wang
 *
 */
@RestController
@RequestMapping("/open")
public class WebUserOpenController extends ControllerBase {

  @Autowired
  private UcUserService ucUserService;

  @Autowired
  private SigningKeyManager signingKeyManager;

  @Autowired
  private List<CreateUserListener> listeners;

  /**
   * app 获得token
   * 
   * @param request
   * @param ucUser
   * @return
   */
  @RequestMapping(value = "/authorize")
  public R.Data user(HttpServletRequest request, HttpServletResponse response, UcUser ucUser) {
    ucUser.setUcName("admin");
    UcUser findUser = new UcUser();
    // ucUserService.findByUcName(ucUser.getUcName());
    findUser.setId(1L);

    if (findUser == null) {
      return R.data(S.S_3002);
    }

    String jwt = JWTUtil.generateToken(KeysConstants.JWT_KEY_BYTES, FrameworkConstants.APP_CODE,
        3600L, new HashMap() {
          {
            put("uid", findUser.getId());
            put("ucName", findUser.getUcName());
          }
        });
    response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
    return R.success("token 有效期为 1天", jwt);
  }

  /**
   * SSO
   * 
   * @param jwt
   * @return
   */
  @RequestMapping(value = "/token/login", method = RequestMethod.GET)
  public R.Data tokenLogin(HttpServletResponse response, HttpServletRequest request, String jwt,
      String redirect) {
    if (StringUtils.isBlank(jwt)) {
      return R.data(S.S_3001);
    }
    byte[] signingKey = null;
    try {
      signingKey = signingKeyManager.getSigningKeyFromJwt(jwt);
    } catch (Exception e) {
      return R.data(S.S_403);
    }
    if (signingKey == null) {
      return R
          .fail(String.format("jwt中，不存在Issuer,或者没有配置对应的keys,Claims=%s", JWTUtil.getJwsNoSign(jwt)));
    }
    try {
      JWTUtil.validateToken(jwt, signingKey);
    } catch (Exception e) {
      return R.fail(e.getMessage());
    }
    // 验证成功
    // 使用项目自己的keys,重新生成token
    String newJwt = new JwtRecreateGenerator(jwt) {
      {
        setIssuer(FrameworkConstants.APP_CODE);
        setExpiration(3600000L);
        add("username", getFromOld("username"));
      }
    }.build(KeysConstants.JWT_KEY_BYTES);
    CookieUtil.create(response, "jwt", newJwt, false, 1111, "localhost");

    if (StringUtils.isNotBlank(redirect)) {
      try {
        response.sendRedirect(redirect);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return R.success(newJwt);
  }

}
