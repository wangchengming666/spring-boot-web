package com.template.datasource;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.template.mapper")
public class MybatisConfig {

  @Bean(name = "DataSource")
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource dataSource() {

    // return new com.alibaba.druid.pool.DruidDataSource();
    return DataSourceBuilder.create().build();
  }

  @Bean
  public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource());

    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sqlSessionFactoryBean
        .setMapperLocations(resolver.getResources("classpath:/mybatisMapper/*.xml"));

    return sqlSessionFactoryBean.getObject();
  }
}
