package com.web.framework.common.factory;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 无状态 subjectFactory
 * 
 * @author cm_wang
 *
 */
public class StatelessSubjectFactory extends DefaultWebSubjectFactory {


  public Subject createSubject(SubjectContext context) {
    // 不创建 session
    context.setSessionCreationEnabled(false);
    return super.createSubject(context);
  }

}
