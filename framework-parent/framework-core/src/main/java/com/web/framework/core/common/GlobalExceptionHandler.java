package com.web.framework.core.common;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.web.framework.common.utils.R;
import com.web.framework.common.utils.R.Data;
import com.web.framework.common.utils.S;

/**
 * 全局异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {
  protected Logger logger = LoggerFactory.getLogger(getClass());

  // 声明要捕获的异常
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public R.Data defultExcepitonHandler(HttpServletRequest request, Exception e) {
    logger.error(e.getMessage(), e);

    return new Data(S.S_FAIL, ExceptionUtils.getMessage(e));
  }
}
