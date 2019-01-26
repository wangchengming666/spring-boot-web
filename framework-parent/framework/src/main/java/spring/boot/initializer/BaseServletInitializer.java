package spring.boot.initializer;

import javax.servlet.Filter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public abstract class BaseServletInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return null;
  }

  /**
   * //初始化spring的根容器RootConfig.class相当于平常配置的applicationContext.xml文件
   */
  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] {};
  }

  @Override
  protected String[] getServletMappings() {
    return null;
  }

}
