package com.web.framework.core.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.framework.core.entity.UcAppUser;
import com.web.framework.core.mapper.UcAppUserMapper;
import com.web.framework.core.service.UcAppUserService;

/**
 * <p>
 * 应用客户端 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@Service
public class UcAppUserServiceImpl extends ServiceImpl<UcAppUserMapper, UcAppUser>
    implements UcAppUserService {

}
