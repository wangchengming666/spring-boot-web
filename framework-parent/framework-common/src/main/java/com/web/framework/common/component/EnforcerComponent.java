package com.web.framework.common.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Assertion;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.file_adapter.FileAdapter;
import org.casbin.jcasbin.rbac.RoleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import com.web.framework.common.manager.SubjectResourceManager;
import com.web.framework.common.manager.UserRoleResourceManager;

/**
 * Enforcer 强制者，访问控制组件
 * 
 */
@Component
public class EnforcerComponent {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  private Enforcer enforcer;
  private boolean modelFromDefault = true;
  private Model model;
  public static String DEFAULT_MODEL_PATH = "config/casbin/model.conf";
  private Adapter adapter;
  public static String DEFAULT_POLICY_PATH = "config/casbin/policy.conf";
  /**
   * 角色的继承关系 应用层已经维护了
   */
  // @Autowired
  private RoleManager roleManager;

  @Autowired(required = false)
  private List<SubjectResourceManager> subjectResourceManagers;

  /**
   * 
   * @param model
   */
  public void setModel(Model model) {
    // 优先判断是不是来自外部
    modelFromDefault = false;
    this.model = model;
  }

  /**
   * 
   * @param modelText 需要换行符
   */
  public void setModel(String modelText) {
    model = new Model();
    model.loadModelFromText(modelText);
  }

  public void setModel(InputStream inputStream) {
    try {
      byte[] data = new byte[inputStream.available()];
      IOUtils.read(inputStream, data);
      setModel(new String(data));
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  public Adapter getAdapter() {
    return adapter;
  }

  public void setAdapter(Adapter adapter) {
    this.adapter = adapter;
  }

  private void init() {
    if (enforcer == null) {
      logger.info("init Enforcer");

      loadModel();
      if (adapter == null && fileExists(DEFAULT_POLICY_PATH)) {
        try {
          setAdapter(
              new FileAdapter(new ClassPathResource(DEFAULT_POLICY_PATH).getFile().getPath()));
        } catch (IOException e) {
          logger.error(e.getMessage(), e);
        }
        logger.info("load casbin policy from {} ", DEFAULT_POLICY_PATH);
      }

      enforcer = new Enforcer(model, adapter);

      if (roleManager != null) {
        enforcer.setRoleManager(roleManager);
      }
      loadRBACPolicy();
      enforcer.enableLog(false);
    }
  }

  private void loadModel() {
    if (modelFromDefault && fileExists(DEFAULT_MODEL_PATH)) {
      modelFromDefault = true;
      try {
        setModel(new ClassPathResource(DEFAULT_MODEL_PATH).getInputStream());
        logger.info("load casbin model from {} ", DEFAULT_MODEL_PATH);
      } catch (IOException e) {
        logger.error(e.getMessage(), e);
      }

    }

  }

  private boolean fileExists(String filepath) {
    return new ClassPathResource(filepath).exists();
  }

  /**
   * 是否有效
   * 
   * @param enable true:有效,false:无效
   */
  public void enableEnforce(boolean enable) {
    init();
    enforcer.enableEnforce(enable);
  }

  public boolean enforce(Object... objects) {
    init();
    return enforcer.enforce(objects);
  }

  public void refresh() {
    if (enforcer == null) {
      init();
    }
    loadModel();
    enforcer.clearPolicy();
    enforcer.loadPolicy();
    loadRBACPolicy();
    printPolicy();
  }

  public void printPolicy() {
    boolean print = true;
    if (print) {
      logger.debug("casbin policy>>>");
      for (Map.Entry<String, Map<String, Assertion>> entry : enforcer.getModel().model.entrySet()) {
        for (Map.Entry<String, Assertion> entry2 : entry.getValue().entrySet()) {
          logger.debug("{},{}", entry2.getKey(), entry2.getValue().value);
        }
      }


      for (List<String> p : enforcer.getPolicy()) {
        logger.debug("p:" + p);
      }
      for (List<String> gp : enforcer.getGroupingPolicy()) {
        logger.debug("g:" + gp);
      }
    }
  }

  public void loadRBACPolicy() {
    if (!CollectionUtils.isEmpty(subjectResourceManagers)) {

      subjectResourceManagers.forEach(t -> {

        for (String[] item : t.subjectResourcePolicy()) {
          enforcer.addPolicy(item);
        }

        if (t instanceof UserRoleResourceManager) {
          UserRoleResourceManager userRoleResourceManager = (UserRoleResourceManager) t;
          for (String[] item : userRoleResourceManager.userRolePolicy()) {
            enforcer.addGroupingPolicy(item);
          }
        }

      });

    }

  }

  public void loadUserRole() {
    enforcer.addPolicy();
  }

  public RoleManager getRoleManager() {
    return roleManager;
  }

  public void setRoleManager(RoleManager roleManager) {
    this.roleManager = roleManager;
  }

}
