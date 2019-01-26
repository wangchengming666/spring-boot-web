package com.web.framework.core.base;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.core.common.TokenUtil;
import com.web.framework.core.entity.UcApp;
import com.web.framework.core.entity.UcRole;
import com.web.framework.core.entity.UcUser;
import com.web.framework.core.service.UcAppService;
import com.web.framework.core.service.UcRoleService;
import com.web.framework.core.service.UcUserService;

/**
 *
 * @author cm_wang
 */
public abstract class ControllerBase {
  protected Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  private UcUserService ucUserService;
  @Autowired
  private UcRoleService ucRoleService;
  @Autowired
  private UcAppService ucAppService;

  public UcUser getUser() {
    Integer uid = TokenUtil.getJwtTokenValue("uid", Integer.class);

    return ucUserService.getById(uid);
  }

  public List<UcRole> getUserRoles() {
    String roleIds = TokenUtil.getJwtTokenValue("roleIds", String.class);

    String[] ARoleIds = StringUtils.split(roleIds, FrameworkConstants.CHAR_DELIMITER);

    return (List<UcRole>) ucRoleService.listByIds(Arrays.asList(ARoleIds));
  }

  public List<String> getUserRoleNames() {
    String roleNames = TokenUtil.getJwtTokenValue("roleNames", String.class);
    return Arrays.asList(StringUtils.split(roleNames, FrameworkConstants.CHAR_DELIMITER));
  }

  public UcApp getApp() {
    String appKey = TokenUtil.getJwtTokenValue("appKey", String.class);

    return ucAppService.getById(appKey);
  }
}
