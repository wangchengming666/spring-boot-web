package com.web.framework.core.controller.admin;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class DemoController extends ControllerBase {
  @Autowired
  private UcUserService ucUserService;

  /**
   * 管理端，统一处理页面请求
   * 
   * @param model
   * @param request
   * @return
   */
  @RequestMapping(value = {"/demo"}, method = RequestMethod.GET)
  @ResponseBody
  public R.Data index(Model model, HttpServletRequest request) {
    return R.success("访问成功！");
  }


}
