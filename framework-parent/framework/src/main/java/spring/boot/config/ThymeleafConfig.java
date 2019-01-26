package spring.boot.config;

import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import com.web.framework.common.dialect.FrameworkDialect;

/**
 * view 解析类<br/>
 * 1.Thymeleaf 在有网络和无网络的环境下皆可运行，即它可以让美工在浏览器查看页面的静态效果，也可以让程序员在服务器查看带数据的动态页面效果。<br/>
 * 2.Thymeleaf 开箱即用的特性。开发人员也可以扩展和创建自定义的方言。 <br/>
 * 3.Thymeleaf 提供spring标准方言和一个与 SpringMVC 完美集成的可选模块，可以快速的实现表单绑定、属性编辑器、国际化等功能。<br/>
 * 
 * @author cm_wang
 *
 */
@EnableConfigurationProperties(ThymeleafProperties.class)
public class ThymeleafConfig {

  protected Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  private FrameworkDialect frameworkDialect;

//  @Bean
//  @ConfigurationProperties(prefix = "spring.thymeleaf")
//  @Primary
//  public ThymeleafProperties thymeleafProperties() {
//    return new ThymeleafProperties();
//  }

  /**
   * 配置模板解析器
   */
  @Bean
  public SpringResourceTemplateResolver templateResolver(ThymeleafProperties properties) {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setPrefix(properties.getPrefix());
    resolver.setSuffix(properties.getSuffix());
    resolver.setTemplateMode(properties.getMode());
    if (properties.getEncoding() != null) {
      resolver.setCharacterEncoding(properties.getEncoding().name());
    }
    resolver.setCacheable(properties.isCache());
    Integer order = properties.getTemplateResolverOrder();
    if (order != null) {
      resolver.setOrder(order);
    }
    resolver.setCheckExistence(properties.isCheckTemplate());
    return resolver;
  }

  /**
   * 配置模板引擎
   */
  @Bean
  public TemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);

    templateEngine.addDialect(frameworkDialect);
    templateEngine.addDialect(new Java8TimeDialect());
    return templateEngine;
  }



  /**
   * 配置Thymeleaf模板视图解析器
   */
  @Bean
  public ViewResolver viewResolver(SpringTemplateEngine templateEngine,
      ThymeleafProperties properties) {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(templateEngine);
    resolver.setCharacterEncoding(properties.getEncoding().name());
    resolver.setContentType(
        appendCharset(properties.getServlet().getContentType(), resolver.getCharacterEncoding()));
    resolver.setExcludedViewNames(properties.getExcludedViewNames());
    resolver.setViewNames(properties.getViewNames());
    // This resolver acts as a fallback resolver (e.g. like a
    // InternalResourceViewResolver) so it needs to have low precedence
    resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
    resolver.setCache(properties.isCache());

    return resolver;
  }

  private String appendCharset(MimeType type, String charset) {
    if (type.getCharset() != null) {
      return type.toString();
    }
    LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
    parameters.put("charset", charset);
    parameters.putAll(type.getParameters());
    return new MimeType(type, parameters).toString();
  }
}
