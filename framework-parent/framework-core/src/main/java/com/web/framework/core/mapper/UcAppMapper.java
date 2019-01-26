package com.web.framework.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.web.framework.core.entity.UcApp;

/**
 * <p>
 * 第三方应用（内部系统，外部系统）,key,secret通过SELECT MD5(RAND()*10000) Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-13
 */
public interface UcAppMapper extends BaseMapper<UcApp> {

}
