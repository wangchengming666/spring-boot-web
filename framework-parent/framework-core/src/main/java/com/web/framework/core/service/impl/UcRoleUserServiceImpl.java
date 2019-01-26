package com.web.framework.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.framework.core.entity.UcRole;
import com.web.framework.core.entity.UcRoleUser;
import com.web.framework.core.entity.UcUser;
import com.web.framework.core.mapper.UcRoleMapper;
import com.web.framework.core.mapper.UcRoleUserMapper;
import com.web.framework.core.mapper.UcUserMapper;
import com.web.framework.core.service.UcRoleService;
import com.web.framework.core.service.UcRoleUserService;

/**
 * <p>
 * 角色与用户 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-10-31
 */
@Service
@Transactional
public class UcRoleUserServiceImpl extends ServiceImpl<UcRoleUserMapper, UcRoleUser>
    implements UcRoleUserService {
  @Autowired
  private UcRoleMapper ucRoleMapper;
  @Autowired
  private UcRoleService ucRoleService;
  @Autowired
  private UcUserMapper ucUserMapper;

  @Override
  @Transactional(readOnly = true)
  public List<UcRole> query(UcUser ucUser, String systemCode) {
    Assert.hasText(systemCode);

    List<UcRoleUser> lists = baseMapper.selectList(
        new QueryWrapper<UcRoleUser>().eq("system_code", systemCode).eq("user_id", ucUser.getId()));
    List<UcRole> reList = new ArrayList<>();
    lists.forEach(v -> {
      UcRole ucRole = ucRoleMapper.selectById(v.getRoleId());
      if (isNormal(ucRole) != null) {
        reList.add(ucRole);
      }
    });

    return reList;
  }

  @Override
  @Transactional(readOnly = true)
  public List<UcUser> query(UcRole ucRole, String sytemCode) {
    List<UcUser> reList = new ArrayList<>();
    List<UcRole> lists = ucRoleService.query(ucRole, sytemCode);
    List<String> ucRolesIds = new ArrayList<>();

    lists.stream().forEach(x -> {
      ucRolesIds.add(String.valueOf(x.getId()));
    });
    List<UcRoleUser> ucRoleUsers = baseMapper.selectList(
        new QueryWrapper<UcRoleUser>().eq("system_code", sytemCode).in("role_id", ucRolesIds));

    for (UcRoleUser ucRoleUser : ucRoleUsers) {
      UcUser ucUser = ucUserMapper.selectById(ucRoleUser.getUserId());
      if (isNormal(ucRole) != null) {
        reList.add(ucUser);
      }
    }

    return reList;
  }

  /**
   * 用户是否正常
   * 
   * @param ucUser
   * @return
   */
  private UcUser isNormal(UcUser ucUser) {
    if (ucUser == null || ucUser.getStatus() != 1) {
      return null;
    } else {
      return ucUser;
    }
  }

  /**
   * 角色是否正常
   * 
   * @param ucUser
   * @return
   */
  private UcRole isNormal(UcRole ucRole) {
    if (ucRole == null || ucRole.getStatus() != 1) {
      return null;
    } else {
      return ucRole;
    }
  }
}
