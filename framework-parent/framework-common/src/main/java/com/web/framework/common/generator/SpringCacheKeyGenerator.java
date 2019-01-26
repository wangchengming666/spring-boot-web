package com.web.framework.common.generator;

import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import com.alibaba.fastjson.JSONObject;

/**
 * cache key 生成策略
 * 
 * @author cm_wang
 *
 */
public class SpringCacheKeyGenerator implements KeyGenerator {

  public static final String KEY = "springCacheKeyGenerator";

  private Logger logger = LoggerFactory.getLogger(getClass());

  private final static int NO_PARAM_KEY = 0;
  /**
   * key前缀，用于区分不同项目的缓存，建议每个项目单独设置<br/>
   * 本项目 :saleDataAnalysis
   */
  private String keyPrefix = "sda";

  @Override
  public Object generate(Object target, Method method, Object... params) {

    char sp = ':';
    StringBuilder strBuilder = new StringBuilder(30);
    strBuilder.append(keyPrefix);
    strBuilder.append(sp);
    // 类名
    strBuilder.append(target.getClass().getSimpleName());
    strBuilder.append(sp);
    // 方法名
    strBuilder.append(method.getName());
    strBuilder.append(sp);
    if (params.length > 0) {
      // 参数值
      for (Object object : params) {
        if (object == null) {
          strBuilder.append(NO_PARAM_KEY);
        } else if (BeanUtils.isSimpleValueType(object.getClass())) {
          strBuilder.append(object);
        } else {
          strBuilder.append(JSONObject.toJSONString(object).hashCode());
        }
      }
    } else {
      strBuilder.append(NO_PARAM_KEY);
    }
    logger.debug(String.format("Cache key=%s", strBuilder.toString()));
    return strBuilder.toString();
  }


  public String getKeyPrefix() {
    return keyPrefix;
  }

  public void setKeyPrefix(String keyPrefix) {
    this.keyPrefix = keyPrefix;
  }
}

