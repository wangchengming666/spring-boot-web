package com.web.framework.common.scan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

/**
 * 类扫描工具
 * 
 * @author cm_wang
 *
 */
public class ClassScan {
  protected static Logger logger = LoggerFactory.getLogger(ClassScan.class);

  /**
   * 实例化，指定包下，指定接口，不带参数的构造类
   * 
   * @param packages 包路
   * @return
   */
  public static <T> List<T> newInstances(String packages, Class<T> interfaceClazz) {
    List<T> lists = new ArrayList<>();
    ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
    try {
      Resource[] resources = resolver.getResources(packages);

      for (Resource resource : resources) {
        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
        Class clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
        if (ClassUtils.isAssignable(interfaceClazz, clazz)) {
          logger.info("newInstance class {}", clazz.getName());
          lists.add((T) clazz.newInstance());
        }
      }

    } catch (IOException | ClassNotFoundException | InstantiationException
        | IllegalAccessException e) {
      logger.error(e.getMessage(), e);
    }

    return lists;
  }

}
