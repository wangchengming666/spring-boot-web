package spring.boot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * ApplicationContext Config 配置类<br/>
 * 初始化spring的根容器ApplicationContextConfig.class相当于平常配置的applicationContext.xml文件 Spring Configuration
 * Class
 *
 */
@ComponentScan(basePackages = "com.web.framework",
    excludeFilters = {@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)})
public class ApplicationContextConfig {
  private Logger logger = LoggerFactory.getLogger(getClass());

}
