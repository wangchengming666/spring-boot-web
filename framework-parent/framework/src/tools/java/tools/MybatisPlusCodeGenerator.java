package tools;

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
    String packageName = "com.myname.skeleton";
    /**
     * application-dev.yml数据库配置的前profix
     */
    String prefix = "spring.datasource";
    /**
     * 表名
     */
    String[] tableNames = new String[] {"CANAL", "NODE"};
    generateByTables(prefix, packageName, tableNames);
  }

  private void generateByTables(String prefix, String packageName, String... tableNames) {
    // user -> UserService, 设置成true: user -> IUserService
    boolean serviceNameStartWithI = false;
    GlobalConfig config = new GlobalConfig();
    MybatisPlusConfig mybatis = new MybatisPlusConfig().setPrefix(prefix);
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
    strategyConfig.setSuperEntityClass("com.myname.skeleton.commons.BaseObject");
    // 输出目录
    config.setActiveRecord(false).setOutputDir(System.getProperty("user.dir") + "//src//main//java")
        .setFileOverride(true);
    if (!serviceNameStartWithI) {
      config.setServiceName("%sService");
    }

    TemplateConfig tc = new TemplateConfig();
    // 不输出web层
    tc.setController(null);

    /**
     * 如果是重新生成,只生成 model
     */
    if (false) {
      tc.setMapper(null);
      tc.setService(null);
      tc.setServiceImpl(null);
      tc.setXml(null);
    }

    new AutoGenerator().setGlobalConfig(config).setDataSource(dataSourceConfig)
        .setStrategy(strategyConfig).setTemplate(tc)
        .setPackageInfo(new PackageConfig().setParent(packageName)
            .setXml("mapper." + mybatis.getSchemaName(dbUrl))
            .setMapper("mapper." + mybatis.getSchemaName(dbUrl))
            .setEntity("entity." + mybatis.getSchemaName(dbUrl))
        // .setController("web.controller")
        ).execute();
  }

}
