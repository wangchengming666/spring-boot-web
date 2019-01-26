package com.web.framework.common.wrapper;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.model.IProcessableElementTag;

public class ElementTagWrapper {

  private IProcessableElementTag tag;

  public ElementTagWrapper(IProcessableElementTag tag) {
    this.tag = tag;
  }

  public String getString(String attributeName) {
    return StringUtils.trim(tag.getAttributeValue(attributeName));
  }

  public String getString(String attributeName, String defaultValue) {
    if (tag.hasAttribute(attributeName)) {
      return StringUtils.trim(tag.getAttributeValue(attributeName));
    } else {
      return defaultValue;
    }
  }

  public Long getLong(String attributeName) {
    if (tag.hasAttribute(attributeName)) {
      return Long.parseLong(getString(attributeName));
    } else {
      return null;
    }
  }

  public Long getLong(String attributeName, long defaultValue) {
    if (tag.hasAttribute(attributeName)) {
      return Long.parseLong(getString(attributeName));
    } else {
      return defaultValue;
    }
  }

  public String[] getStringArray(String attributeName) {
    if (tag.hasAttribute(attributeName)) {
      return StringUtils.split(tag.getAttributeValue(attributeName), ",");
    } else {
      return null;
    }
  }


}
