- 概要

这是基于`spring boot 2.0 + MyBatisPlus 3.0`的轻量级后台管理系统，适用于中小型项目的管理后台。该系统具有基本的用户管理、角色管理、系统日志等最基本的功能，个人和企业可以在此基础上进行开发、扩展、添加各自的需求和业务功能。

- 简介

  - URL组成：`protocol://hostname[:port] / contextPath / servlet / Controller?query`

 - URLs 路径规划(适合小中型项目）
    - /favicon.ico	Favorites Icon 网点图标，不需权限
    - /resource/*	静态资源 在framework/src/main/webapp/resource目录下，会打包到war中，不能修改，只能读取
    - /webfile/* | /static/*	静态资源 在配置文件的${framework.filePath}+/+webfile目录下，可动态管理, 不会打包到war中,静态资源也可移到nginx
    - /admin/*	后台管理功能，只能是用户使用，权限控制到用户，通过账号，认证得到权限，不能当为第三方系统访问
    - /api/*	第三系统接入，只能是系统访问,通过appKey,appSecret获取权限
    - /*	网点主体，用户使用，权限控制到用户，通过spring mvc自定义方法解释，需要权限就拿用户

- 技术选型

    | 技术        | 名称    |  版本  |
    | --------   |---|---|
    | spring boot        | 应用框架      |   2.0.4.RELEASE    |
    | Shiro        | 安全框架      |   1.4    |
    | MyBatisPlus        | ORM框架      |   3.0    |
    | quartz        | 任务调度      |   2.3.0    |
    | casbin        | 认证      |   1.1.0    |
    | fastjson        | 阿里的利器      |   1.2.18    |
    | HikariCP        | spring boot自带的连接池      |   2.7.8    |
    | thymeleaf        | 前端模板      |   3.0    |
    | apache.commons       | 工具包     |   3.7    |
    | hutool       | 工具包，主要用http相关     |   4.4.0    |
    | jasypt       | 加密解密用     |   1.9.2   |            

- 包含的模块

    | 模块名称        | 说明    |
    | --------   |---|
    | framework        | 主体      |
    | framework-client        | Authentication相关     |
    | framework-common        | 共同模块相关，filter、token、listener、jwt等      |
    | framework-core        | 核心，controller相关     |
    | framework-generator        | Mybatis Plus 代码生成工具      |
    | framework-tools        | 工具模块     |

- E-R图

<img width="520" alt="2019-01-27 12 30 12" src="https://user-images.githubusercontent.com/17539174/51813908-a1e44600-22f3-11e9-97d6-2a4594899c76.png">


- 运行环境
  - JDK1.8+
  - MySQL5.6+
  - Maven3.0+
  
- 关于casbin
  - 认证 (authentication) 和授权 (authorization)
    - 认证( authentication)，简写authc
    
    who you are(你是谁) 其实大多数系统都会有用户认证，因为大多数系统都有用户的存在，有用户使用系统就要登录。通常我们使用的用户认证也就是通过验证用户名和密码是否正确。或者再常用的是使用指纹打卡机。系统验证了用户身份的合法性，用户就可访问系统的资源。但是想要不同用户有资源，就引出了用户授权。
    - 授权  (authorization)，简写 authz
    
    what can you do(你可以做什么) 用户授权就是对用户所能访问的资源进程控制。授权过程可以理解为：who对what进行how操作。
    
  - casbin 是一个强大的、高效的开源访问控制框架，其权限管理机制支持多种访问控制模型。
  - Casbin 做了什么
    - 支持自定义请求的格式，默认的请求格式为{subject, object, action}。
    - 具有访问控制模型model和策略policy两个核心概念。
    - 支持RBAC中的多层角色继承，不止主体可以有角色，资源也可以具有角色。
    - 支持超级用户，如 root 或 Administrator，超级用户可以不受授权策略的约束访问任意资源。
    - 支持多种内置的操作符，如 keyMatch，方便对路径式的资源进行管理，如 /foo/bar 可以映射到 /foo*
    
- 参考
  - https://github.com/ueboot/ueboot
  - https://github.com/cjbi/wetech-admin
