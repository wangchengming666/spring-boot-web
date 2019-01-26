package com.web.framework.common.manager;

import java.util.List;

/**
 * 
 * @author cm_wang
 *
 */
public interface SubjectResourceManager {

  /**
   * 所有用户主体与资源<br/>
   * 例 Arrays.asList(new String[] { "r_2", "/admin/*" },<br/>
   * new String[] { "a_d02c3fd73d66ef8fbba99296f6bdedec", "/xx/*" });
   * 
   * @return
   */
  public List<String[]> subjectResourcePolicy();

}
