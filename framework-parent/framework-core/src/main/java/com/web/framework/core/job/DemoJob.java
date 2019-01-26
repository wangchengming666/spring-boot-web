package com.web.framework.core.job;

import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import com.web.framework.common.job.AbstractJob;

/**
 * 
 * ScheduledJob 任务计划实现类
 *
 */
public class DemoJob extends AbstractJob {

  @Override
  public void doJob(int id, ApplicationContext springContext, JobExecutionContext context) {
    System.out.println("Job" + id);
  }


}
