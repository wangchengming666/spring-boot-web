package com.web.framework.core.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.web.framework.common.wrapper.ElementTagWrapper;
import com.web.framework.core.service.UcRoleService;

@Component
public class UcRoleProcessor extends AbstructTableProcessor {

	public UcRoleProcessor() {
		super("role");
	}

	@Override
	protected IPage tableProcess(ITemplateContext context, ElementTagWrapper tagWrapper, IPage page,
			IElementTagStructureHandler structureHandler) {
		return ucRoleService.page(page, new QueryWrapper<>());
	}

	@Autowired
	private UcRoleService ucRoleService;

}
