package com.web.framework.common.dialect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.standard.StandardDialect;

@Component
public class FrameworkDialect extends AbstractProcessorDialect {

  private Set<IProcessor> processors = new HashSet<>();

  public static final String PREFIX = "fwk";

  public FrameworkDialect() {
    super("FrameworkDialect", PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
  }

  @Override
  public Set<IProcessor> getProcessors(String dialectPrefix) {

    return (Set<IProcessor>) this.processors;
  }

  @Autowired(required = false)
  private void processorAttributeTag(List<AbstractAttributeTagProcessor> paramProcessors) {

    for (AbstractAttributeTagProcessor processor : paramProcessors) {
      processors.add(processor);
    }

  }

  @Autowired(required = false)
  private void processorsElementTag(List<AbstractElementTagProcessor> paramProcessors) {

    for (AbstractElementTagProcessor processor : paramProcessors) {
      processors.add(processor);
    }

  }
}
