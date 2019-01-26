package com.web.framework.generator.mybatisplus;

import java.io.File;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

/**
 * 读取 application-dev.yml 连接信息
 * 
 * @author cm_wang
 *
 */
public class MybatisPlusConfig {

  private Properties properties;
  private String prefix = "framework.datasource";

  /**
   * 设置 prefix
   * 
   * @param prefix
   * @return
   */
  public MybatisPlusConfig setPrefix(String prefix) {
    this.prefix = prefix;
    return this;
  }

  public Properties properties() {

    switch (1) {
      // 其它项目中读取
      case 0:
        YamlPropertiesFactoryBean yaml1 = new YamlPropertiesFactoryBean();
        yaml1.setResources(new FileSystemResource(new File(System.getProperty("user.dir")
            + "/../framework/src/main/resources/application-dev.yml")));
        properties = yaml1.getObject();
        break;
      // 从classpath中读取
      case 1:
        YamlPropertiesFactoryBean yaml2 = new YamlPropertiesFactoryBean();
        yaml2.setResources(new ClassPathResource("application-dev.yml"));
        properties = yaml2.getObject();
        break;
      // 直接设置
      case 2:
        properties = new Properties();
        properties.put("jdbc-url",
            "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=round&useSSL=false");
        properties.put("driver-class-name", "com.mysql.jdbc.Driver");
        properties.put("username", "root");
        properties.put("password", "9527");
        break;
    }

    return properties;
  }

  public String getString(String value) {
    init();
    return properties.getProperty(prefix + value);
  }

  private void init() {
    if (properties == null) {
      properties();
    }
  }

  /**
   * 得到URL schema ,表示一个数据库
   * 
   * @param url
   * @return
   */
  public String getSchemaName(String url) {
    String schema = StringUtils.substring(url, StringUtils.lastIndexOf(url, "/") + 1,
        StringUtils.lastIndexOf(url, "?"));
    return schema;
  }

}
