package com.template.serviceimpl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.template.mapper.DataBaseMapper;
import com.template.service.GetCountService;

@Service
public class GetCountServiceImpl implements GetCountService{

  @Resource
  DataBaseMapper dataBaseMapper;
  
  @Override
  public int getCount(String tableName) {
    
    return dataBaseMapper.getCount(tableName);
  }

}
