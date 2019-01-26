package spring.boot.config;

import java.io.File;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import com.web.framework.common.constants.FrameworkConstants;

/**
 * Server 容器配置
 * 
 * @author cm_wang
 *
 */
public class ServerConfig {
  /**
   * Enable Multiple Connectors with Tomcat
   * 
   * @return
   */
  @Bean
  public ServletWebServerFactory servletContainer() {

    return onlyHttpTomcat();
  }

  /**
   * 只有http
   * 
   * @return
   */
  private ServletWebServerFactory onlyHttpTomcat() {
    return new TomcatServletWebServerFactory();
  }

  /**
   * 只有https,如果http会自动转到https上
   * 
   * @return
   */
  private ServletWebServerFactory onlyHttpsTomcat() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {

      @Override
      protected void postProcessContext(Context context) {

        // 禁用不需要的http方法
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
        // 设置使用httpOnly
        context.setUseHttpOnly(true);

      }
    };


    // https支持
    tomcat.addAdditionalTomcatConnectors(createSslConnector());
    return tomcat;
  }

  /**
   * https支持
   * 
   * @return
   */
  private Connector createSslConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
    File keystore = new File(FrameworkConstants.ROOT_DIR, "keys/www.2tiger.site_keystore.jks");
    // File truststore = new ClassPathResource("keystore").getFile();
    connector.setScheme("https");
    connector.setSecure(true);
    // 443端口即网页浏览端口，主要是用于HTTPS服务，是提供加密和通过安全端口传输的另一种HTTP。
    //在linux 占用443 需要root权限 
    connector.setPort(443);
    connector.setRedirectPort(443);


    protocol.setSSLEnabled(true);
    // Keystore，用来存放服务端证书，可以看成一个放key的库，key就是公钥，私钥，数字签名等组成的一个信息。
    protocol.setKeystoreFile(keystore.getAbsolutePath());
    protocol.setKeystorePass("xxooxxoo");
    // protocol.setTruststoreFile(truststore.getAbsolutePath());
    // protocol.setTruststorePass("changeit");
    protocol.setKeyAlias("tomcat");
    return connector;
  }
}
