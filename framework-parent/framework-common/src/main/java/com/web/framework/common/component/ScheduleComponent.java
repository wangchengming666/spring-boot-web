package com.web.framework.common.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 管理所有任务
 * 
 * @author cm_wang
 *
 */
@Component
public class ScheduleComponent {
  protected Logger logger = LoggerFactory.getLogger(getClass());
  /**
  * 
  */
  public static final String ID = "id";

  public static final String APPLICATION_CONTEXT = "applicationContext";

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired(required = false)
  private Scheduler scheduler;

  /**
   * 创建任务计划
   * 
   * @param id
   * @param jobClass
   * @param cronExpression
   */
  public void create(Integer id, Class<? extends Job> jobClass, String cronExpression) {
    String taskName = getTaskName(id);
    JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(taskName).build();
    create(id, jobDetail, cronExpression, null, null);
  }

  /**
   * 创建任务计划
   * 
   * @param id
   * @param jobClass
   * @param intervalInSeconds
   * @param repeatCount
   */
  public void create(Integer id, Class<? extends Job> jobClass, Integer intervalInSeconds,
      Integer repeatCount) {
    String taskName = getTaskName(id);
    JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(taskName).build();
    create(id, jobDetail, null, intervalInSeconds, repeatCount);
  }


  public void create(Integer id, JobDetail jobDetail, String cronExpression,
      Integer intervalInSeconds, Integer repeatCount) {
    String taskName = getTaskName(id);
    TriggerKey triggerKey = TriggerKey.triggerKey(taskName);
    try {
      Trigger trigger = scheduler.getTrigger(triggerKey);
      if (null == trigger) {
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put(ID, id);
        jobDataMap.put(APPLICATION_CONTEXT, applicationContext);

        ScheduleBuilder<? extends Trigger> builder =
            scheduleBuilder(cronExpression, intervalInSeconds, repeatCount);

        trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(builder)
            .startNow().build();

        scheduler.scheduleJob(jobDetail, trigger);
      } else {
        ScheduleBuilder builder = scheduleBuilder(cronExpression, intervalInSeconds, repeatCount);
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(builder)
            .startNow().build();
        scheduler.rescheduleJob(triggerKey, trigger);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  /**
   * 创建 scheduleBuilder
   * 
   * @param cronExpression
   * @param intervalInSeconds
   * @param repeatCount
   * @return
   */
  private ScheduleBuilder<? extends Trigger> scheduleBuilder(String cronExpression,
      Integer intervalInSeconds, Integer repeatCount) {

    if (StringUtils.isNoneBlank(cronExpression)) {
      return CronScheduleBuilder.cronSchedule(cronExpression);
    } else if (intervalInSeconds != null && repeatCount != null) {
      return SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(intervalInSeconds)
          .withRepeatCount(repeatCount);
    } else {
      throw new IllegalArgumentException("无法创建 scheduleBuilder");
    }

  }

  public List<String> listJob() {
    List<String> jobs = new ArrayList<>();

    try {
      for (String groupName : scheduler.getJobGroupNames()) {

        for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

          String jobName = jobKey.getName();
          String jobGroup = jobKey.getGroup();

          // get job's trigger
          List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
          Date nextFireTime = triggers.get(0).getNextFireTime();
          jobs.add("[jobName]:" + jobName + " [groupName]:" + jobGroup + " 下次执行时间:" + nextFireTime);

        }
      }
    } catch (SchedulerException e) {
      logger.error(e.getMessage(), e);
    }

    return jobs;
  }


  /**
   * 执行任务计划
   * 
   * 
   * @param id
   */
  public void runOnce(Integer id) {
    if (id != null) {
      try {
        scheduler.triggerJob(JobKey.jobKey(getTaskName(id)));
      } catch (SchedulerException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  /**
   * 暂停任务计划
   * 
   * 
   * @param id
   */
  public void pause(Integer id) {
    if (id != null) {
      try {
        scheduler.pauseJob(JobKey.jobKey(getTaskName(id)));
      } catch (SchedulerException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  /**
   * 恢复任务计划
   * 
   * 
   * @param id
   */
  public void resume(Integer id) {
    if (id != null) {
      try {
        scheduler.resumeJob(JobKey.jobKey(getTaskName(id)));
      } catch (SchedulerException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  /**
   * 删除任务计划
   * 
   * @param id
   */
  public void delete(Integer id) {
    if (id != null) {
      try {
        scheduler.deleteJob(JobKey.jobKey(getTaskName(id)));
      } catch (SchedulerException e) {
        logger.error(e.getMessage(), e);
      }
    }
  }

  /**
   * 任务计划名称
   * 
   * @param id
   * @return task name
   */
  public String getTaskName(Integer id) {
    return "task-" + id;
  }
}
