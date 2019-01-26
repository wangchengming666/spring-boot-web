package com.web.framework.core.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.framework.core.entity.UcUserEmail;
import com.web.framework.core.mapper.UcUserEmailMapper;
import com.web.framework.core.service.UcUserEmailService;

/**
 * <p>
 * 用户注册邮件 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@Service
public class UcUserEmailServiceImpl extends ServiceImpl<UcUserEmailMapper, UcUserEmail>
    implements UcUserEmailService {

}
