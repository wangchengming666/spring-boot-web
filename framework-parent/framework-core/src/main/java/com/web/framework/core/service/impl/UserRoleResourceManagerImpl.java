package com.web.framework.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.manager.UserRoleResourceManager;
import com.web.framework.core.entity.UcRoleAuthorized;
import com.web.framework.core.entity.UcRoleUser;
import com.web.framework.core.service.UcRoleAuthorizedService;
import com.web.framework.core.service.UcRoleUserService;

@Service
public class UserRoleResourceManagerImpl implements UserRoleResourceManager {

  @Autowired
  private UcRoleUserService ucRoleUserService;

  @Autowired
  private UcRoleAuthorizedService ucRoleAuthorizedService;

  @Override
  public List<String[]> userRolePolicy() {
    List<String[]> re = new ArrayList<>();
    // 暂不考虑 用户，角色状态
    ucRoleUserService.list(new QueryWrapper<UcRoleUser>()).stream().forEach(t -> {
      re.add(new String[] {FrameworkConstants.USER_PREFIX + t.getUserId(),
          FrameworkConstants.ROLE_PREFIX + t.getRoleId()});
    });

    return re;
  }

  @Override
  public List<String[]> subjectResourcePolicy() {
    // 暂不考虑 用户，角色状态
    List<String[]> re = new ArrayList<>();
    ucRoleAuthorizedService.list(new QueryWrapper<UcRoleAuthorized>()).stream().forEach(t -> {
      re.add(new String[] {FrameworkConstants.ROLE_PREFIX + t.getRoleId(), t.getUrl()});
    });

    return re;
  }

}
