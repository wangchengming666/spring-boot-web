package spring.boot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import com.web.framework.common.listener.ApplicationEnvironmentPreparedListener;

/**
 * 常量配置<br/>
 * 必须同时实现EnvironmentAware, BeanFactoryPostProcessor 两个接口?<br/>
 * 通过 ApplicationEnvironmentPreparedListener 实现
 * 
 * @author cm_wang
 *
 */
public class ConstantsConfig implements EnvironmentAware, EnvironmentPostProcessor {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private ConfigurableEnvironment env;

  private ApplicationEnvironmentPreparedListener applicationListener =
      new ApplicationEnvironmentPreparedListener();


  @Override
  public void setEnvironment(Environment environment) {
    if (environment instanceof ConfigurableEnvironment) {
      this.env = (ConfigurableEnvironment) environment;
    }

    applicationListener.setEnv(env);
    applicationListener.handleEnvironment();
  }



  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment,
      SpringApplication application) {}
}
