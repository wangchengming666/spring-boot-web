package com.web.framework.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.manager.SubjectResourceManager;
import com.web.framework.core.entity.UcAppAuthorized;
import com.web.framework.core.service.UcAppAuthorizedService;

@Service
public class AppResourceManager implements SubjectResourceManager {

  @Autowired
  private UcAppAuthorizedService ucAppAuthorizedService;

  @Override
  public List<String[]> subjectResourcePolicy() {
    List<String[]> re = new ArrayList<>();
    ucAppAuthorizedService.list(new QueryWrapper<UcAppAuthorized>()).stream().forEach(t -> {
      re.add(new String[] {FrameworkConstants.APP_PREFIX + t.getAppKey(), t.getUrl()});
    });

    return re;
  }

}
