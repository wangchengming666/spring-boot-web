package com.web.framework.core.controller.api;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.constants.KeysConstants;
import com.web.framework.common.listener.CreateUserListener;
import com.web.framework.common.utils.JWTUtil;
import com.web.framework.common.utils.R;
import com.web.framework.common.utils.S;
import com.web.framework.core.base.ControllerBase;
import com.web.framework.core.entity.UcApp;
import com.web.framework.core.model.vo.UserVO;
import com.web.framework.core.service.UcAppService;
import com.web.framework.core.service.UcUserService;

/**
 * 
 * @author cm_wang
 *
 */
@RestController
@RequestMapping("/open")
public class AppOpenController extends ControllerBase {
  @Autowired
  private UcUserService ucUserService;
  @Autowired
  private UcAppService ucAppService;

  @Autowired
  private List<CreateUserListener> listeners;


  /**
   * 用户登录获取token
   * 
   * @param request
   * @param ucUser
   * @return
   */
  @RequestMapping(value = "/authorize")
  public R.Data user(HttpServletRequest request, UcApp ucApp) {
    if (StringUtils.isBlank(ucApp.getAppSecret())) {
      return R.data(S.S_3001);
    }

    UcApp findApp = ucAppService.getOne(new QueryWrapper<UcApp>()
        .eq(StringUtils.isNotBlank(ucApp.getAppKey()), "app_key", ucApp.getAppKey())
        .eq(StringUtils.isNotBlank(ucApp.getAppCode()), "app_code", ucApp.getAppCode())
        .eq(StringUtils.isNotBlank(ucApp.getAppSecret()), "app_secret", ucApp.getAppSecret()));

    if (findApp == null) {
      return R.data(S.S_1002);
    }

    String jwt = JWTUtil.generateToken(KeysConstants.JWT_KEY_BYTES, FrameworkConstants.APP_CODE,
        86400L, new HashMap() {
          {
            put("appKey", findApp.getAppKey());
          }
        });

    return R.success("token 有效期为 1天", jwt);
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public R.Data list(UserVO user, HttpServletRequest request) {

    return R.success(ucUserService.list(user));
  }
}
