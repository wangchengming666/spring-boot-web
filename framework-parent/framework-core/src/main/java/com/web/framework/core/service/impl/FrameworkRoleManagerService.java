package com.web.framework.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.manager.FrameworkRoleManager;
import com.web.framework.core.entity.UcRole;
import com.web.framework.core.entity.UcUser;
import com.web.framework.core.mapper.UcRoleMapper;
import com.web.framework.core.mapper.UcUserMapper;
import com.web.framework.core.service.UcRoleService;
import com.web.framework.core.service.UcRoleUserService;

/**
 * 框架自身安全管理 ，作用在 /admin/*<br/>
 * 要校验状态
 * 
 * @author cm_wang
 *
 */
@Service
@Transactional
public class FrameworkRoleManagerService extends FrameworkRoleManager {

  @Autowired
  private UcRoleMapper ucRoleMapper;
  @Autowired
  private UcRoleService ucRoleService;

  @Autowired
  private UcUserMapper ucUserMapper;

  @Autowired
  private UcRoleUserService ucRoleUserService;

  @Override
  @Transactional(readOnly = true)
  public boolean hasLink(String name1, String name2, String... domain) {
    Assert.hasText(name1, FrameworkConstants.MESSAGE_HASTEXT);
    Assert.hasText(name2, FrameworkConstants.MESSAGE_HASTEXT);
    // 匿名用户没有组
    if (isAnonym(name1) || isAnonym(name2)) {
      return false;
    }

    String systemCode = "";
    if (domain.length >= 1) {
      systemCode = domain[0];
    } else {
      systemCode = FrameworkConstants.APP_CODE;
    }
    Assert.hasText(systemCode, FrameworkConstants.MESSAGE_HASTEXT);

    String roleId2 = StringUtils.substringAfter(name2, FrameworkConstants.ROLE_PREFIX);
    /**
     * 查询角色继承
     */
    if (StringUtils.startsWith(name1, FrameworkConstants.ROLE_PREFIX)) {
      String roleId1 = StringUtils.substringAfter(name1, FrameworkConstants.ROLE_PREFIX);
      List<UcRole> lists = ucRoleMapper
          .selectList(new QueryWrapper<UcRole>().eq("system_code", FrameworkConstants.APP_CODE)
              .eq("id", roleId2).like("child_ids", roleId1).eq("status", 1));

      boolean hasLink = false;
      for (UcRole role : lists) {
        if (role.getStatus() == 1) {
          hasLink = true;
          break;
        }
      }

      return hasLink;
      /**
       * 查询用户角色
       */
    } else if (StringUtils.startsWith(name1, FrameworkConstants.USER_PREFIX)) {

      UcUser ucUser = isNormalUser(name1);
      if (ucUser == null) {
        return false;
      }

      List<UcRole> ucRoles = ucRoleUserService.query(ucUser, FrameworkConstants.APP_CODE);
      for (UcRole item : ucRoles) {
        if (roleId2.equalsIgnoreCase(String.valueOf(item.getId()))) {
          return true;
        }
      }

    }
    return false;
  }

  public boolean hasLink(String name1, String name2) {

    return hasLink(name1, name2, FrameworkConstants.APP_CODE);
  }

  @Override
  @Transactional(readOnly = true)
  public List<String> getRoles(String name, String... domain) {
    if (StringUtils.startsWith(name, FrameworkConstants.ROLE_PREFIX)) {
      UcRole ucRole = isNormalRole(name);
      if (ucRole == null) {
        return Collections.EMPTY_LIST;
      }

      List<UcRole> ucRoles = ucRoleService.query(ucRole, FrameworkConstants.APP_CODE);

      return toRoleIds(ucRoles);
    } else if (StringUtils.startsWith(name, FrameworkConstants.USER_PREFIX)) {
      UcUser ucUser = isNormalUser(name);
      if (ucUser == null) {
        return Collections.emptyList();
      }
      List<UcRole> ucRoles = ucRoleUserService.query(ucUser, FrameworkConstants.APP_CODE);

      return toRoleIds(ucRoles);

    } else {
      throw new IllegalArgumentException();
    }

  }

  @Override
  @Transactional(readOnly = true)
  public List<String> getUsers(String name) {
    Assert.isTrue(StringUtils.startsWith(name, FrameworkConstants.ROLE_PREFIX),
        FrameworkConstants.MESSAGE_ISTRUE);

    UcRole ucRole = isNormalRole(name);
    if (ucRole == null) {
      return Collections.EMPTY_LIST;
    }

    List<UcUser> lists = ucRoleUserService.query(ucRole, FrameworkConstants.APP_CODE);

    return toUserIds(lists);
  }

  private List<String> toUserIds(List<UcUser> lists) {
    List<String> reList = new ArrayList<>();
    for (UcUser user : lists) {
      reList.add(FrameworkConstants.USER_PREFIX + user.getId());
    }
    return reList;
  }

  private List<String> toRoleIds(List<UcRole> lists) {
    List<String> reList = new ArrayList<>();
    for (UcRole user : lists) {
      reList.add(FrameworkConstants.USER_PREFIX + user.getId());
    }
    return reList;
  }

  /**
   * 角色是否正常
   * 
   * @param ucUser
   * @return
   */
  private UcRole isNormalRole(String name) {
    UcRole ucRole =
        ucRoleMapper.selectById(StringUtils.substringAfter(name, FrameworkConstants.ROLE_PREFIX));
    return isNormal(ucRole);
  }

  /**
   * 用户是否正常
   * 
   * @param ucUser
   * @return
   */
  private UcUser isNormalUser(String name) {
    UcUser ucUser =
        ucUserMapper.selectById(StringUtils.substringAfter(name, FrameworkConstants.USER_PREFIX));
    return isNormal(ucUser);
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
