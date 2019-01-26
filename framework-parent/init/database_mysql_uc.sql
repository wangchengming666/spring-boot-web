DROP TABLE IF EXISTS `uc_user`;
CREATE TABLE uc_user (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'uid',
  uc_name varchar(50) DEFAULT NULL COMMENT 'uc_name',
  upper_uc_name varchar(50) DEFAULT NULL COMMENT '大写的uc_name',
  identity_card char(20) DEFAULT NULL COMMENT '身份证号码',
  true_name varchar(20) DEFAULT NULL COMMENT '真实姓名',
  nick_name varchar(45) DEFAULT NULL COMMENT '昵称',
  status tinyint(4) NOT NULL DEFAULT 1 COMMENT '用户状态,1:正常,0:禁用,2:销户',
  PRIMARY KEY (id),
  UNIQUE KEY `uc_name` (`uc_name`),
  UNIQUE KEY `identity_card` (`identity_card`)
)
ENGINE = INNODB, COMMENT = 'UC用户基本信息';

DROP TABLE IF EXISTS `uc_user_cellphone`;
CREATE TABLE uc_user_cellphone (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id bigint(20) NOT NULL COMMENT 'uid',
  cellphone varchar(30) DEFAULT NULL COMMENT '手机号码',
  status tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态,1:正常,0:禁用',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `update_user` varchar(255) NOT NULL COMMENT 'uid',
  PRIMARY KEY (id),
  KEY user_id (user_id)
)
ENGINE = INNODB, COMMENT = '用户注册手机';

DROP TABLE IF EXISTS `uc_user_email`;
CREATE TABLE uc_user_email (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id bigint(20) NOT NULL COMMENT 'uid',
  email varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  status tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态,1:正常,0:禁用',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `update_user` varchar(255) NOT NULL COMMENT 'uid',
  PRIMARY KEY (id),
  KEY user_id (user_id)
)
ENGINE = INNODB, COMMENT = '用户注册邮件';

DROP TABLE IF EXISTS `uc_role`;
CREATE TABLE uc_role (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  child_ids text DEFAULT NULL COMMENT '子分类ID',
  parent_id bigint(20) DEFAULT NULL COMMENT '父角色ID',
  name varchar(100) NOT NULL COMMENT '角色名字',
  status tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态,1:正常,0:禁用',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `update_user` varchar(255) NOT NULL COMMENT 'uid',
  PRIMARY KEY (id)
)
ENGINE = INNODB, COMMENT = '角色';

DROP TABLE IF EXISTS `uc_role_user`;
CREATE TABLE uc_role_user (
  role_id int(11) NOT NULL COMMENT '角色ID',
  user_id bigint(20) NOT NULL COMMENT 'uid',
  PRIMARY KEY (role_id, user_id)
)
ENGINE = INNODB, COMMENT = '角色与用户';

DROP TABLE IF EXISTS `uc_role_resource`;
CREATE TABLE uc_role_resource (
  resource_id bigint(20) NOT NULL COMMENT '资源 ID',
  role_id int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (resource_id, role_id)
)
ENGINE = INNODB, COMMENT = '角色与资源关系';

DROP TABLE IF EXISTS `uc_role_authorized`;
CREATE TABLE `uc_role_authorized` (
  role_id int(11) NOT NULL COMMENT '角色ID',
  `url` varchar(100) NOT NULL COMMENT '授权地址（菜单与按钮)',
  PRIMARY KEY (`role_id`, url)
)
ENGINE = INNODB, COMMENT = '角色授权地址';

DROP TABLE IF EXISTS `uc_resource`;
CREATE TABLE uc_resource (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  url varchar(255) DEFAULT NULL COMMENT '链接地址，菜单',
  authorized_url text DEFAULT NULL COMMENT '授权地址(多个按,分割)',
  attached varchar(300) DEFAULT NULL COMMENT '标题附加样式',
  parent_resource_id bigint(20) DEFAULT NULL COMMENT '父模块',
  is_menu tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否菜单，1：是',
  sort int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `update_user` varchar(255) NOT NULL COMMENT 'uid',
  PRIMARY KEY (id)
)
ENGINE = INNODB, COMMENT = '系统资源';

-- ----------------------------
-- 以上是 用户，角色，资源，权限，系统自身应用
-- 以下是第三方应用相关（内部系统，外部系统，都属于第三方系统）
-- ----------------------------

DROP TABLE IF EXISTS `uc_app`;
CREATE TABLE `uc_app` (
  `app_key` varchar(50) NOT NULL COMMENT 'APP key',
  `app_code` varchar(50) DEFAULT NULL COMMENT 'APP code(内部系统方便使用)',
  `app_secret` varchar(50) NOT NULL COMMENT 'APP secret',
  name varchar(100) DEFAULT NULL COMMENT '系统名称',
  `description` varchar(300) DEFAULT NULL COMMENT '描述',
  `expiry_minutes` int(11) DEFAULT NULL COMMENT '过期时间',
  `update_user` varchar(255) NOT NULL COMMENT 'uid',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`app_key`),
  UNIQUE KEY `key` (`app_code`)
)
ENGINE = INNODB, COMMENT = '第三方应用（内部系统，外部系统）,key,secret通过SELECT MD5(RAND()*10000)';


DROP TABLE IF EXISTS `uc_app_authorized`;
CREATE TABLE `uc_app_authorized` (
  `app_key` varchar(50) NOT NULL COMMENT 'APP key',
  `url` varchar(100) NOT NULL COMMENT '授权地址',
  PRIMARY KEY (`app_key`, url)
)
ENGINE = INNODB, COMMENT = '应用授权地址';


DROP TABLE IF EXISTS `uc_app_user`;
CREATE TABLE `uc_app_user` (
  id bigint(20) NOT NULL COMMENT 'ID',
  `app_key` varchar(50) NOT NULL COMMENT 'APP key',
  `user_id` bigint(20) DEFAULT NULL COMMENT '绑定用户',
  `client_version` varchar(50) DEFAULT NULL COMMENT '版本',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '上次登录IP',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  `disabled` tinyint(1) NOT NULL COMMENT '是否禁用',
  PRIMARY KEY (`id`)
)
ENGINE = INNODB, COMMENT = '应用客户端';


DROP TABLE IF EXISTS `uc_app_token`;
CREATE TABLE `uc_app_token` (
  `access_token` varchar(20) NOT NULL COMMENT '授权验证',
  `app_key` varchar(50) NOT NULL COMMENT 'APP key',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `expiry_date` datetime DEFAULT NULL COMMENT '过期日期',
  KEY `create_date` (`create_date`)
)
ENGINE = INNODB, COMMENT = '应用授权(如果用jwt，可以不使用这张表';



INSERT INTO uc_user (id, uc_name, upper_uc_name, identity_card, true_name, nick_name, status)
  VALUES (1, 'admin', 'ADMIN', NULL, NULL, '超级管理员', 1);

INSERT INTO uc_app(app_key, app_code, app_secret, name, description, expiry_minutes, update_user, update_time)
	VALUES('d02c3fd73d66ef8fbba99296f6bdedec', 'DEMO', '3bb57c516c03432cb39cfccd407c0a24', 'DEMO', NULL, 60, '', '2018-12-13 14:36:00');

  
