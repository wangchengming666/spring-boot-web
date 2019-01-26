package spring.boot.initializer;

import spring.boot.config.ApiServletConfig;

/**
 * <p>
 * Servlet3.0 工程入口类
 * </p>
 * <p>
 * WebInitializer
 * </p>
 *
 */
public class ApiInitializer extends BaseServletInitializer {

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {ApiServletConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/api/*"};
  }

  @Override
  protected String getServletName() {
    return "【api servlet】";
  }
}
