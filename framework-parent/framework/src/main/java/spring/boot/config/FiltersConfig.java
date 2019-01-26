package spring.boot.config;

import java.util.Arrays;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import com.web.framework.common.constants.FrameworkConstants;
import com.web.framework.common.filter.CorsFilter;
import com.web.framework.common.filter.JwtUpdateFilter;
import com.web.framework.common.filter.LogFilter;
import com.web.framework.common.filter.xss.XssFilter;

/**
 * Filter 配置
 * 
 * // spring boot 会按照order值的大小，从小到大的顺序来依次过滤。
 * 
 * @author cm_wang
 *
 */
public class FiltersConfig {
  /**
   * 全局 日志
   * 
   * @return
   */
  @Bean
  @Order(Integer.MIN_VALUE)
  @ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev")
  public FilterRegistrationBean<Filter> logFilter() {
    FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
    registration.setDispatcherTypes(DispatcherType.REQUEST);
    registration.setFilter(new LogFilter() {
      {
        setIgnorePaths(Arrays.asList(new String[] {"/resource", "/favicon.ico"}));
      }
    });
    registration.addUrlPatterns("/*");
    registration.setName(LogFilter.FILTER_NAME);
    return registration;
  }

  /**
   * 全局 编码
   * 
   * @return
   */
  @Bean
  @Order(Integer.MIN_VALUE + 10)
  public FilterRegistrationBean<Filter> characterEncodingFilter() {
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setEncoding(FrameworkConstants.ENCODING_NAME);
    characterEncodingFilter.setForceEncoding(true);

    FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
    registration.setDispatcherTypes(DispatcherType.REQUEST);
    registration.setFilter(characterEncodingFilter);
    registration.addUrlPatterns("/*");
    registration.setName("characterEncodingFilter");
    return registration;
  }

  /**
   * 全局 XSS
   * 
   * 跨站脚本攻击(Cross Site Scripting)<br/>
   * 为不和层叠样式表(Cascading Style Sheets, CSS) 的缩写混淆，故将跨站脚本攻击缩写为XSS。<br/>
   * 恶意攻击者往Web页面里插入恶意html代码，当用户浏览该页之时，嵌入其中Web里面的html代码会被执行，从而达到恶意攻击用户的特殊目的。
   * 
   */
  @Bean
  @Order(Integer.MIN_VALUE + 20)
  public FilterRegistrationBean<Filter> xssFilterRegistration() {
    FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
    registration.setDispatcherTypes(DispatcherType.REQUEST);
    registration.setFilter(new XssFilter());
    registration.addUrlPatterns("/*");
    registration.setName(XssFilter.FILTER_NAME);
    return registration;
  }

  /**
   * 局部 api 进行 cors
   * 
   * @return
   */
  @Bean
  @Order(Integer.MIN_VALUE + 30)
  public FilterRegistrationBean<Filter> corsFilterRegistration() {
    FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
    registration.setDispatcherTypes(DispatcherType.REQUEST);
    registration.setFilter(new CorsFilter());
    registration.addUrlPatterns("/api/*");
    registration.setName(CorsFilter.FILTER_NAME);
    return registration;
  }

  /**
   * 全局 shiro
   * 
   * @return
   */
  @Bean
  @Order(Integer.MAX_VALUE - 10)
  public FilterRegistrationBean<Filter> shiroFilterRegistration() {
    FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
    registration.setFilter(new DelegatingFilterProxy(ShiroFilterConfig.FILTER_NAME));
    // 该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
    registration.addInitParameter("targetFilterLifecycle", "true");
    registration.setEnabled(true);
    registration.addUrlPatterns("/*");
    return registration;
  }

  @Bean
  @Order(Integer.MAX_VALUE)
  public FilterRegistrationBean<Filter> jwtExpirationFilter() {
    FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
    registration.setDispatcherTypes(DispatcherType.REQUEST);
    registration.setFilter(new JwtUpdateFilter());
    registration.addUrlPatterns("/*");
    registration.setName(JwtUpdateFilter.FILTER_NAME);
    return registration;
  }
}
