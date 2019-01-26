package com.web.framework.core.controller.admin;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.constants.KeysConstants;
import com.web.framework.common.listener.CreateUserListener;
import com.web.framework.common.utils.JWTUtil;
import com.web.framework.common.utils.R;
import com.web.framework.common.utils.S;
import com.web.framework.core.base.ControllerBase;
import com.web.framework.core.entity.UcUser;
import com.web.framework.core.service.UcUserService;

/**
 * 
 * @author cm_wang
 *
 */
@RestController
@RequestMapping("/open")
public class UserOpenController extends ControllerBase {

  @Autowired
  private UcUserService ucUserService;

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
  public R.Data user(HttpServletRequest request, UcUser ucUser) {
    ucUser.setUcName("admin");
    UcUser findUser = ucUserService.findByUcName(ucUser.getUcName());
    // findUser.setId(1L);

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

    return R.success("token 有效期为 1天", jwt);
  }


}
