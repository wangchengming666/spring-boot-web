package spring.boot.config;

import java.util.List;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

/**
 * mybatis plus 配置
 * 
 * @author cm_wang
 *
 */
public class MybatisPlusConfig {
  protected Logger logger = LoggerFactory.getLogger(getClass());

  /***
   * plus 的性能优化,日志打印,开发环境使用
   * 
   * @return
   */
  @Bean
  @ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev")
  public PerformanceInterceptor performanceInterceptor() {
    PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
    /***
     * <!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->
     */
    performanceInterceptor.setMaxTime(200);
    /***
     * <!--SQL是否格式化 默认false-->
     */
    performanceInterceptor.setFormat(true);
    return performanceInterceptor;
  }

  /**
   * mybatis-plus分页插件<br>
   * 文档：http://mp.baomidou.com<br>
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    paginationInterceptor.setDialectType("mysql");
    return paginationInterceptor;
  }

  /**
   * 相当于顶部的： {@code @MapperScan("com.baomidou.springboot.mapper*")} 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径
   */
  @Bean
  public MapperScannerConfigurer mapperScannerConfigurer() {
    MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
    scannerConfigurer.setBasePackage("com.web.framework.core.mapper*");
    scannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
    return scannerConfigurer;
  }

  @Bean("sqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource,
      @Autowired List<Interceptor> interceptors) throws Exception {
    /**
     * 
     */
    String mapperLocations = "classpath:/config/mapper/*Mapper.xml";
    String configLocation = "classpath:/config/mapper/mybatis-config.xml";
    String typeAliasesPackage = "com.web.framework.core.entity.**";

    MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(dataSource); // 数据源
    sqlSessionFactory.setGlobalConfig(globalConfig()); // 全局配置

    Interceptor[] desc = new Interceptor[interceptors.size()];
    sqlSessionFactory.setPlugins(interceptors.toArray(desc)); // 分页插件
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    try {
      // 自动扫描Mapping.xml文件
      sqlSessionFactory.setMapperLocations(resolver.getResources(mapperLocations));
      sqlSessionFactory.setConfigLocation(resolver.getResource(configLocation));
      sqlSessionFactory.setTypeAliasesPackage(typeAliasesPackage);
      return sqlSessionFactory.getObject();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw e;
    }
  }

  /**
   * 全局配置，可参考官网
   * 
   * @return
   */
  public GlobalConfig globalConfig() {

    GlobalConfig config = new GlobalConfig();
    config.setDbConfig(new GlobalConfig.DbConfig().setDbType(DbType.MYSQL));
    return config;
  }

}
