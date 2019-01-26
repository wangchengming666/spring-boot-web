package spring.boot.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import spring.boot.initializer.AdminInitializer;
import spring.boot.initializer.ApiInitializer;
import spring.boot.initializer.CommonInitializer;
import spring.boot.initializer.WebInitializer;

/**
 * servlet Initializer
 * 
 * @author cm_wang
 *
 */
@Import(CommonServletConfig.class)
public class InitializerConfig {
	@Bean
	public ServletContextInitializer webInitializer() {
		return new ServletContextInitializer() {

			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				new WebInitializer().onStartup(servletContext);
			}

		};
	}

	@Bean
	public ServletContextInitializer apiInitializer() {
		return new ServletContextInitializer() {

			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				new ApiInitializer().onStartup(servletContext);
			}

		};
	}

	/**
	 * 与 @Import(CommonServletConfig.class) 结合使用
	 * 
	 * @return
	 */
	@Bean
	public ServletContextInitializer resourceInitializer() {
		return new ServletContextInitializer() {

			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				new CommonInitializer().onStartup(servletContext);
			}

		};
	}

	@Bean
	public ServletContextInitializer adminInitializer() {
		return new ServletContextInitializer() {

			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				new AdminInitializer().onStartup(servletContext);
			}

		};
	}

}
