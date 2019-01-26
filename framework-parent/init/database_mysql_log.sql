DROP TABLE IF EXISTS `log_login`;
CREATE TABLE `log_login` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  uc_name varchar(50) DEFAULT NULL COMMENT 'uc_name',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) COMMENT = '登录日志';


DROP TABLE IF EXISTS `log_operation`;
CREATE TABLE `log_operation` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP',
   uc_name varchar(50) DEFAULT NULL COMMENT 'uc_name',
  `operate_code` varchar(40) NOT NULL COMMENT '代码',
  `operate_value` varchar(40) DEFAULT NULL COMMENT '操作',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `content` text DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) COMMENT = '操作日志';



DROP TABLE IF EXISTS `log_task`;
CREATE TABLE `log_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL COMMENT '任务',
  `begintime` datetime NOT NULL COMMENT '开始时间',
  `endtime` datetime DEFAULT NULL COMMENT '结束时间',
  `success` tinyint(1) NOT NULL COMMENT '执行成功',
  `result` longtext COMMENT '执行结果',
  PRIMARY KEY (`id`),
  KEY `task_id` (`task_id`),
  KEY `success` (`success`)
) COMMENT = '任务计划日志';