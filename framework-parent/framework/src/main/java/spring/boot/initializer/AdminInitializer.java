package spring.boot.initializer;

import spring.boot.config.AdminServletConfig;

/**
 * <p>
 * 管理后台初始化
 * </p>
 * 
 * <p>
 * Management Initializer
 * </p>
 *
 */
public class AdminInitializer extends BaseServletInitializer {

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[] {AdminServletConfig.class};
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/admin/*"};
  }

  @Override
  protected String getServletName() {
    return "【admin servlet】";
  }

}
