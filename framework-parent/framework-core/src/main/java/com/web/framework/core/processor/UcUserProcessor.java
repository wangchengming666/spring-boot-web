package com.web.framework.core.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.framework.common.wrapper.ElementTagWrapper;
import com.web.framework.core.service.UcUserService;

@Component
public class UcUserProcessor extends AbstructTableProcessor {

  public UcUserProcessor() {
    super("user");
  }


  @Override
  protected IPage tableProcess(ITemplateContext context, ElementTagWrapper tagHelper, IPage page,
      IElementTagStructureHandler structureHandler) {
    return ucUserService.page(page, new QueryWrapper<>());
  }

  @Autowired
  private UcUserService ucUserService;

}
