package com.web.framework.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注解<br/>
 * <ul>
 * @Target:注解的作用目标
 * 
 * <li>@Target(ElementType.TYPE)   //接口、类、枚举、注解
 * <li>@Target(ElementType.FIELD) //字段、枚举的常量
 * <li>@Target(ElementType.METHOD) //方法
 * <li>@Target(ElementType.PARAMETER) //方法参数
 * <li>@Target(ElementType.CONSTRUCTOR)  //构造函数
 * <li>@Target(ElementType.LOCAL_VARIABLE)//局部变量
 * <li>@Target(ElementType.ANNOTATION_TYPE)//注解
 * <li>@Target(ElementType.PACKAGE) ///包   
 * 
 * @author cm_wang
 * 
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {



}
