package com.web.framework.core.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.framework.core.entity.UcApp;
import com.web.framework.core.mapper.UcAppMapper;
import com.web.framework.core.service.UcAppService;

/**
 * <p>
 * 第三方应用（内部系统，外部系统）,key,secret通过SELECT MD5(RAND()*10000) 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
@Service
public class UcAppServiceImpl extends ServiceImpl<UcAppMapper, UcApp> implements UcAppService {

}
