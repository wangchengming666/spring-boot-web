package spring.boot.initializer;

import spring.boot.config.WebServletConfig;

/**
 * 
 *
 */
public class WebInitializer extends BaseServletInitializer {

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebServletConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/*" };
	}

	@Override
	protected String getServletName() {
		return "【web servlet】";
	}

}
