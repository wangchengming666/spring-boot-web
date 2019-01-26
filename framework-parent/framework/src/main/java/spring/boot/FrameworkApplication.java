package spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import com.web.framework.common.listener.ApplicationEnvironmentPreparedListener;
import com.web.framework.common.listener.ApplicationFailedListener;
import com.web.framework.common.listener.ApplicationStartedListener;
import com.web.framework.common.listener.ApplicationStartingListener;
import spring.boot.config.ApplicationContextConfig;
import spring.boot.config.AspectConfig;
import spring.boot.config.DataSourceConfig;
import spring.boot.config.FiltersConfig;
import spring.boot.config.InitializerConfig;
import spring.boot.config.MybatisPlusConfig;
import spring.boot.config.ServerConfig;
import spring.boot.config.ShiroFilterConfig;
import spring.boot.config.ThymeleafConfig;

/**
 * 不使用 @SpringBootApplication，细粒度控制扫描范围<br/>
 * 项目中文件的编码和程序使用的编码 全部为 FrameworkConstants.ENCODING_NAME UTF-8<br/>
 * https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#spring-web
 * 
 * @author cm_wang
 */
@Import({ServerConfig.class, ApplicationContextConfig.class, FiltersConfig.class,
    InitializerConfig.class, AspectConfig.class, ThymeleafConfig.class, ShiroFilterConfig.class,
    MybatisPlusConfig.class, DataSourceConfig.class})
public class FrameworkApplication {

  public static void main(String[] args) {

    SpringApplication app = new SpringApplication(FrameworkApplication.class);
    app.addListeners(new ApplicationListener[] {new ApplicationFailedListener(),
        new ApplicationStartedListener(), new ApplicationStartingListener(),
        new ApplicationEnvironmentPreparedListener()});

    app.run(args);
  }


}
