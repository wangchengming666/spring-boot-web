package com.web.framework.core.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.web.framework.core.entity.LogOperation;
import com.web.framework.core.mapper.LogOperationMapper;
import com.web.framework.core.service.LogOperationService;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-18
 */
@Service
public class LogOperationServiceImpl extends ServiceImpl<LogOperationMapper, LogOperation>
    implements LogOperationService {



}
