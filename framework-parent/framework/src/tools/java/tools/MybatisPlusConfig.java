package tools;

import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

/**
 * 读取 application-dev.yml 连接信息
 * 
 * @author cm_wang
 *
 */
public class MybatisPlusConfig {

  private Properties properties;
  private String prefix = "spring.datasource";

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
    YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
    yaml.setResources(new ClassPathResource("application-dev.yml"));
    properties = yaml.getObject();
    return properties;
  }

  public String getString(String value) {
    init();
    return properties.getProperty(prefix + "." + value);
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
