package com.web.framework.core.controller.admin;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.web.framework.core.base.ControllerBase;


/**
 * 
 * @author cm_wang
 *
 */
@Controller
public class IndexController extends ControllerBase {

  /**
   * 管理端，统一处理页面请求
   * 
   * @param model
   * @param request
   * @return
   */
  @RequestMapping(value = {"/index", "**/*.html"}, method = RequestMethod.GET)
  public String index(Model model, HttpServletRequest request) {
    return StringUtils.substringBefore(request.getRequestURI(), ".html");
  }

  
}
