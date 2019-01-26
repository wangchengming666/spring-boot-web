package com.web.framework.core.aspect;

import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.web.framework.common.annotation.UserOpreationLog;
import com.web.framework.common.constants.UserOperationConstants;
import com.web.framework.common.utils.IdUtil;
import com.web.framework.core.common.TokenUtil;
import com.web.framework.core.entity.LogOperation;
import com.web.framework.core.service.LogOperationService;

/**
 * 日志切面, 只能放在 Service层
 * 
 * @author cm_wang
 * @see https://my.oschina.net/u/3434392/blog/1625493
 */
@Aspect
public class UserOpreationAspect {
  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private LogOperationService logOperationService;

  /**
   * 申明一个切点 里面是 execution表达式,通过注解
   */
  @Pointcut("@annotation(com.web.framework.common.annotation.UserOpreationLog)")
  public void pointCut() {}

  /**
   * 
   * @param point
   */
  // @Before(value = "pointCut()")
  public void before(JoinPoint point) {}

  /**
   * 
   * @param point
   */
  // @After(value = "pointCut()")
  public void after(JoinPoint point) {}

  /**
   * 
   */
  @Around(value = "pointCut() && @annotation(userOpreationLog)", argNames = "userOpreationLog")
  public Object around(ProceedingJoinPoint point, UserOpreationLog userOpreationLog)
      throws Throwable {
    Object result = null;
    Integer uId = TokenUtil.getJwtTokenValue("uid", Integer.class);
    String ucName = TokenUtil.getJwtTokenValue("ucName", String.class);
    try {
      result = point.proceed();
    } catch (Throwable e) {
      process(uId, ucName, UserOperationConstants.ERR);
      throw e;
    }
    process(uId, ucName, userOpreationLog.code());
    return result;
  }

  private void process(Integer uId, String ucName, UserOperationConstants userOperationConstants) {
    if (uId != null && userOperationConstants != null) {
      LogOperation logOperation = new LogOperation();
      logOperation.setId(IdUtil.nextId());

      logOperation.setUserId(uId.longValue());
      logOperation.setUcName(ucName);

      logOperation.setOperateCode(userOperationConstants.getCode());
      logOperation.setOperateValue(userOperationConstants.getValue());

      logOperation.setCreateDate(LocalDateTime.now());

      logOperationService.save(logOperation);
    }
  }

}
