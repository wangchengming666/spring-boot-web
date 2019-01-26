package spring.boot.initializer;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.core.Conventions;
import org.springframework.util.ObjectUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.filter.CharacterEncodingFilter;
import com.web.framework.common.constants.FrameworkConstants;

/**
 * Common 通用 servlet Initializer<br/>
 * ResourceInitializer Servlet3.0 工程入口类
 *
 */
public class CommonInitializer implements WebApplicationInitializer {
  /**
	 * 默认名字，不可改。
	 */
	public static String DEFAULT_SERVLET_NAME = "defaultServlet";
	/**
	 * 静态资源
	 */
	public static String WEBFILE_SERVLET_NAME = "webfileServlet";
	/**
	 * 静态资源路径
	 */
	private String[] resourceMappings = new String[] { "/resource/*", "/favicon.ico" };

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		ServletRegistration.Dynamic registration = servletContext.addServlet(DEFAULT_SERVLET_NAME,
				new HttpRequestHandlerServlet());
		registration.setLoadOnStartup(1);
		registration.addMapping(resourceMappings);

		ServletRegistration.Dynamic webfileRegistration = servletContext.addServlet(WEBFILE_SERVLET_NAME,
				new HttpRequestHandlerServlet());
		webfileRegistration.setLoadOnStartup(0);
		webfileRegistration.addMapping(new String[] { "/webfile/*", "/static/*" });

		Filter[] filters = getServletFilters();
		if (!ObjectUtils.isEmpty(filters)) {
			for (Filter filter : filters) {
				registerServletFilter(servletContext, filter,
						new String[] { DEFAULT_SERVLET_NAME, WEBFILE_SERVLET_NAME });
			}
		}

	}

	protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter,
			String[] servletNames) {
		String filterName = Conventions.getVariableName(filter);
		FilterRegistration.Dynamic registration = servletContext.addFilter(filterName, filter);

		if (registration == null) {
			int counter = 0;
			while (registration == null) {
				if (counter == 100) {
					throw new IllegalStateException("Failed to register filter with name '" + filterName + "'. "
							+ "Check if there is another filter registered under the same name.");
				}
				registration = servletContext.addFilter(filterName + "#" + counter, filter);
				counter++;
			}
		}
		registration.addMappingForServletNames(getDispatcherTypes(), false, servletNames);
		return registration;
	}

	private EnumSet<DispatcherType> getDispatcherTypes() {
		return (true
				? EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
						DispatcherType.ASYNC)
				: EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE));
	}

	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(FrameworkConstants.ENCODING_NAME);
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] {};
	}
}
