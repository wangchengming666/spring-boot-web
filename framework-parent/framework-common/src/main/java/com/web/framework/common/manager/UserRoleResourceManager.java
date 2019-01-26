package com.web.framework.common.manager;

import java.util.List;

/**
 * 
 * @author cm_wang
 *
 */
public interface UserRoleResourceManager extends SubjectResourceManager {

  /**
   * 所有 用户与角色<br/>
   * 例:Arrays.asList(new String[] { "u_1", "r_2" }, new String[] { "u_7", "r_2" });
   * 
   * @return
   */
  public List<String[]> userRolePolicy();

}
