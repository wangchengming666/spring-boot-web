package com.web.framework.generator.mybatisplus;

import java.io.File;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * Mybatis Plus 代码生成工具
 * 
 * @author cm_wang
 *
 */
public class MybatisPlusCodeGenerator {

  public static void main(String[] args) {
    new MybatisPlusCodeGenerator().generateCode();
  }

  /*********************************
   * 配置
   */
  public void generateCode() {
    // 包名
    String packageName = "com.web.framework.core";
    /**
     * application-dev.yml数据库配置的前prefix
     */
    String prefix = "framework.datasource.";
    /**
     * 表名
     */
    String[] tableNames = new String[] {"uc_app",
        "uc_app_authorized",
        "uc_app_token   ",
        "uc_app_user",
        "uc_resoure",
        "uc_role",
        "uc_role_authorized",
        "uc_role_resource",
        "uc_role_user",
        "uc_user",
        "uc_user_cellphone",
        "uc_user_email"};
    tableNames = new String[] {"log_operation"};
    generateByTables(prefix, packageName, tableNames);
  }

  private void generateByTables(String prefix, String packageName, String... tableNames) {
    // user -> UserService, 设置成true: user -> IUserService
    boolean serviceNameStartWithI = false;
    GlobalConfig config = new GlobalConfig();
    MybatisPlusConfig mybatis = new MybatisPlusConfig().setPrefix(prefix);
    System.out.println(mybatis.properties());
    // 数据库配置
    String dbUrl = mybatis.getString("jdbc-url");
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl)
        .setUsername(mybatis.getString("username")).setPassword(mybatis.getString("password"))
        .setDriverName(mybatis.getString("driver-class-name"));

    // 生成策略配置
    StrategyConfig strategyConfig = new StrategyConfig();
    // 全局大写命名 ORACLE 注意
    strategyConfig.setCapitalMode(true).setEntityLombokModel(false)
        .setNaming(NamingStrategy.underline_to_camel).setInclude(tableNames);// 修改替换成你需要的表名，多个表名传数组
    /**
     * 设置超类
     */
    // strategyConfig.setSuperEntityClass("com.myname.skeleton.commons.BaseObject");
    // 输出目录
    config.setActiveRecord(false)
        .setOutputDir(System.getProperty("user.dir") + File.separatorChar + ".."
            + File.separatorChar + "framework-core" + File.separatorChar + "src"
            + File.separatorChar + "main" + File.separatorChar + "java")
        .setFileOverride(true);
    if (!serviceNameStartWithI) {
      config.setServiceName("%sService");
    }

    TemplateConfig tc = new TemplateConfig();
    // 不输出web层
    tc.setController(null);

    /**
     * 如果是重新生成true,只生成 model<br/>
     * false,全部生成
     * 
     */
    if (false) {
      tc.setMapper(null);
      tc.setService(null);
      tc.setServiceImpl(null);
      tc.setXml(null);
    }

    new AutoGenerator().setGlobalConfig(config).setDataSource(dataSourceConfig)
        .setStrategy(strategyConfig).setTemplate(tc).setPackageInfo(new PackageConfig()
            .setParent(packageName).setXml("mapper").setMapper("mapper").setEntity("entity")
        // .setController("web.controller")
        ).execute();
  }

}
