package spring.boot.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import com.web.framework.common.factory.StatelessSubjectFactory;
import com.web.framework.common.filter.CasbinAccessControlFilter;
import com.web.framework.common.filter.JwtAuthcFilter;
import com.web.framework.common.realm.JwtRealm;

/**
 * Shiro 基本概念<br/>
 * <p>
 * Subject: Subject即主体,可理解为“当前用户”<br/>
 * SecurityManager: SecurityManager即安全管理器，对全部的subject进行安全管理，它是shiro的核心<br/>
 * realm:从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager要验证用户身份，那么它需要从Realm获取相应的用户进行比较以确定用户身份是否合法；相当于datasource数据源<br/>
 * Authenticator: Authenticator即认证器，对用户身份进行认证，Authenticator是一个接口<br/>
 * CredentialsMatcher:密码匹配器<br/>
 * Session Manager：会话管理<br/>
 * </p>
 * （1）认证过滤器：anon、authcBasic、auchc、user、logout
 * 
 * （2）授权过滤器：perms、roles、ssl、rest、port
 */
public class ShiroFilterConfig {

  public final static String FILTER_NAME = "shiroFilter";

  @Bean
  @Primary
  public Realm jwtRealm() {

    return new JwtRealm();
  }

  @Bean
  public CasbinAccessControlFilter casbinFilter() {
    return new CasbinAccessControlFilter();
  }

  @Bean
  public SessionManager sessionManager() {
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setSessionIdUrlRewritingEnabled(false);
    sessionManager.setSessionIdCookieEnabled(false);
    sessionManager.setSessionValidationSchedulerEnabled(false);
    return sessionManager;
  }

  @Bean
  public DefaultSubjectDAO StatelessDefaultSubjectDAO() {
    DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
    DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
    sessionStorageEvaluator.setSessionStorageEnabled(false);
    subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
    return subjectDAO;
  }

  /**
   * shiro 核心
   * 
   * @param oAuth2Realm
   * @param sessionManager
   * @return
   */
  @Bean
  public SecurityManager securityManager(SessionManager sessionManager, Realm realm,
      DefaultSubjectDAO defaultSubjectDAO) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setSessionManager(sessionManager);
    securityManager.setRealm(realm);
    // 关闭 cookies RememberMe
    securityManager.setRememberMeManager(null);
    securityManager.setSubjectDAO(defaultSubjectDAO);
    securityManager.setSubjectFactory(new StatelessSubjectFactory());
    return securityManager;
  }

  @Bean(ShiroFilterConfig.FILTER_NAME)
  public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager,
      CasbinAccessControlFilter casbinACFilter) {
    ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
    shiroFilter.setSecurityManager(securityManager);

    // 自定义filter
    Map<String, Filter> filters = new HashMap<>();
    filters.put(JwtAuthcFilter.FILTER_NAME, new JwtAuthcFilter() {
      {
        setExcludePaths(new ArrayList<String>() {
          {
            add("/admin/open");
            add("/api/open");
            add("/open");
          }
        });
      }
    });

    filters.put(CasbinAccessControlFilter.FILTER_NAME, casbinACFilter);
    shiroFilter.setFilters(filters);

    Map<String, String> filterMap = new HashMap<String, String>() {
      {
        put("/admin/**", JwtAuthcFilter.FILTER_NAME); // authc
        put("/api/**", JwtAuthcFilter.FILTER_NAME);
        put("/** ", CasbinAccessControlFilter.FILTER_NAME); // authz
      }
    };
    /**
     * 配置规则 拦截器的优先级是从上到下，从左到右，如果有匹配的拦截器就会阻断并返回。
     */
    shiroFilter.setFilterChainDefinitionMap(filterMap);

    return shiroFilter;
  }

}
