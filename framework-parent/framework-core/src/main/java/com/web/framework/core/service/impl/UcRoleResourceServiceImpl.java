package com.web.framework.core.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.framework.core.entity.UcRoleResource;
import com.web.framework.core.mapper.UcRoleResourceMapper;
import com.web.framework.core.service.UcRoleResourceService;

/**
 * <p>
 * 角色与资源关系 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@Service
public class UcRoleResourceServiceImpl extends ServiceImpl<UcRoleResourceMapper, UcRoleResource>
    implements UcRoleResourceService {

}
