package com.web.framework.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.web.framework.common.constants.UserOperationConstants;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@UserLog
// @Document：说明该注解将被包含在javadoc中
public @interface UserOpreationLog {

  UserOperationConstants code();

}
