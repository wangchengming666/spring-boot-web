package spring.boot.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.setScheduler(taskExecutor());
  }

  @Bean(destroyMethod = "shutdown")
  public Executor taskExecutor() {
    return Executors.newScheduledThreadPool(100);
  }
}
