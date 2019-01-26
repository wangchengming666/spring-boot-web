package com.web.framework.core.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web.framework.core.entity.UcRole;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-11-07
 */
public interface UcRoleService extends IService<UcRole> {

  /**
   * 查询本身和子角色
   * 
   * @param ucRole
   * @param systemCode
   * @return
   */
  public List<UcRole> query(UcRole ucRole, String systemCode);
}
