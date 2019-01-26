package spring.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.web.framework.core.aspect.UserOpreationAspect;

@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AspectConfig {

  @Bean
  public UserOpreationAspect logApiAspect() {
    return new UserOpreationAspect();
  }

}
