package spring.boot.config;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * 
 * WebConfig WebServlet配置类
 *
 */
@EnableWebMvc
@ComponentScan(basePackages = "com.web.framework.core.controller.api",
    useDefaultFilters = false, includeFilters = {@ComponentScan.Filter(value = {Controller.class})})
public class ApiServletConfig implements WebMvcConfigurer {

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // RequestMappingHandlerAdapter
    converters.add(utf8StringHttpMessageConverter());
    // converters.add(new FormHttpMessageConverter());
    // converters.add(new MappingJackson2HttpMessageConverter());
    converters.add(fastJsonHttpMessageConverters());
  }

  @Bean
  public FastJsonHttpMessageConverter fastJsonHttpMessageConverters() {
    // 1.需要先定义一个Convert 转换消息的对象；
    FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

    // 2.添加fastjson的配置信息，比如：是否要格式化返回就送数据；
    FastJsonConfig fastJsonConfig = new FastJsonConfig();
    // SerializerFeature 描述
    // WriteNullListAsEmpty 将Collection类型字段的字段空值输出为[]
    // WriteNullStringAsEmpty 将字符串类型字段的空值输出为空字符串 ""
    // WriteNullNumberAsZero 将数值类型字段的空值输出为0
    // WriteNullBooleanAsFalse 将Boolean类型字段的空值输出为false
    fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
    fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
        SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty,
        SerializerFeature.WriteDateUseDateFormat);

    // 解决Long转json精度丢失的问题
    SerializeConfig serializeConfig = SerializeConfig.globalInstance;
    serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
    serializeConfig.put(Long.class, ToStringSerializer.instance);
    serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
    fastJsonConfig.setSerializeConfig(serializeConfig);

    // 3.在Convert中添加配置信息；
    fastConverter.setFastJsonConfig(fastJsonConfig);

    return fastConverter;
  }

  // @Bean
  public StringHttpMessageConverter utf8StringHttpMessageConverter() {
    StringHttpMessageConverter utf8StringHttpMessageConverter = new StringHttpMessageConverter();
    utf8StringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
    return utf8StringHttpMessageConverter;
  }
}
