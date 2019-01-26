package com.web.framework.core.controller.api;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.web.framework.common.utils.R;
import com.web.framework.core.base.ControllerBase;
import com.web.framework.core.model.vo.UserVO;
import com.web.framework.core.service.UcUserService;

/**
 * 
 * @author cm_wang
 *
 */
@Controller
@RequestMapping("/user")
public class UserManagerController extends ControllerBase {
  @Autowired
  private UcUserService ucUserService;


  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @ResponseBody
  public R.Data list(UserVO user, HttpServletRequest request) {

    return R.success(ucUserService.list(user));
  }

}
