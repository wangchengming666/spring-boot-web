package spring.boot.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.handler.WebFileHttpRequestHandler;

/**
 * 通用servlet 配置
 */
public class CommonServletConfig implements WebMvcConfigurer {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 资源处理器
	 * 
	 * @return default servlet httprequesthandler
	 */
	@Bean(name = "defaultServlet")
	public HttpRequestHandler defaultServlet() {
		return new DefaultServletHttpRequestHandler();
	}

	/**
	 * 站点静态资源处理器<br/>
	 * 直接访问，不通过权限
	 * 
	 */
	@Bean(name = "webfileServlet")
	public HttpRequestHandler webfileServlet() {
		return new WebFileHttpRequestHandler();
	}
}
