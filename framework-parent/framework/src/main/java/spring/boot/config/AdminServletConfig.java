package spring.boot.config;

import java.util.List;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * AdminServlet配置类
 * 
 * AdminConfig
 *
 * useDefaultFilters：指示是否自动检测类的注释
 * 
 * @Component * @Repository , * @Service , or * @Controller 应启用。
 */

@EnableWebMvc
@ComponentScan(basePackages = "com.web.framework.core.controller.admin",
    useDefaultFilters = false,
    includeFilters = {@ComponentScan.Filter(value = {Controller.class,})})
public class AdminServletConfig implements WebMvcConfigurer {

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    WebMvcConfigurer.super.addArgumentResolvers(resolvers);
  }


}
