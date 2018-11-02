package com.template.mapper;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface DataBaseMapper {

  int getCount(@Param("tableName") String tableName);
}
