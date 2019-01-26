package com.web.framework.core.processor;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.templatemode.TemplateMode;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.web.framework.common.dialect.FrameworkDialect;
import com.web.framework.common.wrapper.ElementTagWrapper;

import io.jsonwebtoken.lang.Assert;

/**
 * List对象抽象类,通过mybatis plus page支持分页
 * 
 * @author cm_wang
 *
 */
public abstract class AbstructTableProcessor extends AbstractAttributeTagProcessor {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 * 
	 * @param attrName
	 *            表名
	 */
	public AbstructTableProcessor(String attrName) {
		super(TemplateMode.HTML, FrameworkDialect.PREFIX, null, false, attrName, true,
				StandardDialect.PROCESSOR_PRECEDENCE, true);
		Assert.hasLength(attrName);
	}

	/**
	 * <tr fwk:role="" id="11" var="it">
	 */
	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {
		WebEngineContext _context = null;
		if (context instanceof WebEngineContext) {
			_context = (WebEngineContext) context;
		}
		ElementTagWrapper tagHelper = new ElementTagWrapper(tag);
		String iterableName = tagHelper.getString("_var", "it");
		String statName = tagHelper.getString("_stat", "stat");
		String varPageName = tagHelper.getString("_page", "page");
		Long count = tagHelper.getLong("_count", 10);
		Long index = tagHelper.getLong("_index", 1);
		String[] asc = tagHelper.getStringArray("_asc");
		String[] desc = tagHelper.getStringArray("_desc");

		Page page = new Page(index, count);
		page.setAsc(asc);
		page.setDesc(desc);

		IPage resultPage = tableProcess(context, tagHelper, page, structureHandler);

		structureHandler.iterateElement(iterableName, statName, resultPage.getRecords());
		structureHandler.removeAttribute("_var");
		structureHandler.removeAttribute("_stat");

		//XXX 可能有更好的方法
		if (_context.getRequest().getAttribute(varPageName) != null) {
			logger.warn("当前页码，存在同名的page变量名，自动+1");
			String number = StringUtils.substringAfterLast(varPageName, "_");
			if (StringUtils.isBlank(number)) {
				_context.getRequest().setAttribute(varPageName + "_1", resultPage);
			} else {
				_context.getRequest().setAttribute(varPageName + "_" + Integer.parseInt(number) + 1, resultPage);
			}
		} else {
			_context.getRequest().setAttribute(varPageName, resultPage);
		}

	}

	protected abstract IPage tableProcess(final ITemplateContext context, final ElementTagWrapper tagWrapper,
			final IPage page, final IElementTagStructureHandler structureHandler);

}
