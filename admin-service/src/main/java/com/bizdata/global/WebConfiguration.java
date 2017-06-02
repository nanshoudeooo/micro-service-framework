package com.bizdata.global;

import com.bizdata.common.String2MenuTypeConverter;
import com.bizdata.common.String2ResourceTypeConverter;
import me.sdevil507.convert.JpaPageConditionConverter;
import me.sdevil507.convert.JpaSortConditionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置相关转换类
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //新增一个排序VO类转换
        registry.addConverter(new JpaSortConditionConverter());
        //新增一个分页VO类转换
        registry.addConverter(new JpaPageConditionConverter());
        //新增一个ResourceType类型转换
        registry.addConverter(new String2ResourceTypeConverter());
        //新增一个MenuType类型转换
        registry.addConverter(new String2MenuTypeConverter());
        super.addFormatters(registry);
    }
}
