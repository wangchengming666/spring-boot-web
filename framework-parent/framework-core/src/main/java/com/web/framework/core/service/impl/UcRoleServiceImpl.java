package com.web.framework.core.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.core.entity.UcRole;
import com.web.framework.core.mapper.UcRoleMapper;
import com.web.framework.core.service.UcRoleService;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-11-07
 */
@Service
public class UcRoleServiceImpl extends ServiceImpl<UcRoleMapper, UcRole> implements UcRoleService {

  @Override
  public List<UcRole> query(UcRole ucRole, String systemCode) {

    String[] childIds = StringUtils.split(ucRole.getChildIds(), FrameworkConstants.CHAR_DELIMITER);
    String[] ucRoleIds = ArrayUtils.add(childIds, String.valueOf(ucRole.getId()));
    List<UcRole> ucUsers = baseMapper.selectBatchIds(Arrays.asList(ucRoleIds));

    return ucUsers.stream().filter(x -> isNormal(x) != null).collect(Collectors.toList());
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
