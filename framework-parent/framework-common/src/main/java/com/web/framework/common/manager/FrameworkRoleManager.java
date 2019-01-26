package com.web.framework.common.manager;

import org.apache.commons.lang3.StringUtils;
import org.casbin.jcasbin.rbac.RoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.web.framework.common.constants.AuthConstants;

public abstract class FrameworkRoleManager implements RoleManager {
  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void addLink(String arg0, String arg1, String... arg2) {

  }

  @Override
  public void clear() {

  }

  @Override
  public void deleteLink(String arg0, String arg1, String... arg2) {

  }

  @Override
  public void printRoles() {

  }

  protected boolean isAnonym(String name) {
    return StringUtils.equalsIgnoreCase(name, AuthConstants.ANONYM);
  }

}
