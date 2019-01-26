package com.web.framework.common.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.web.framework.common.component.ScheduleComponent;

/**
 * QuartzJobBean 的简单封装
 * 
 * @author cm_wang
 *
 */
public abstract class AbstractJob extends QuartzJobBean {

  protected Logger logger = LoggerFactory.getLogger(getClass());
  protected ApplicationContext springContext;
  protected Integer id;
  protected JobDataMap jobDataMap;

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    jobDataMap = context.getJobDetail().getJobDataMap();
    springContext = (ApplicationContext) jobDataMap.get(ScheduleComponent.APPLICATION_CONTEXT);
    id = (Integer) jobDataMap.get(ScheduleComponent.ID);

    try {
      doJob(id, springContext, context);
    } catch (Throwable e) {
      logger.error("执行job id {} 出错", id, e);
      throw e;
    }

  }

  /**
   * 执行Job
   * 
   * @param id
   * @param springContext
   * @param context
   */
  public abstract void doJob(int id, ApplicationContext springContext, JobExecutionContext context);



}
