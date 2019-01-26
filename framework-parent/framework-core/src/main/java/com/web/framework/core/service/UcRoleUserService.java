package com.web.framework.core.service;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.web.framework.core.entity.UcRole;
import com.web.framework.core.entity.UcRoleUser;
import com.web.framework.core.entity.UcUser;

/**
 * <p>
 * 角色与用户 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-10-31
 */
public interface UcRoleUserService extends IService<UcRoleUser> {

  public List<UcRole> query(UcUser ucUser, String systemCode);

  public List<UcUser> query(UcRole ucRole, String sytemCode);
}
