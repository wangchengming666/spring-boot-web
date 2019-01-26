package com.web.framework.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.web.framework.core.entity.UcApp;

/**
 * <p>
 * 第三方应用（内部系统，外部系统）,key,secret通过SELECT MD5(RAND()*10000) 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
public interface UcAppService extends IService<UcApp> {

}
