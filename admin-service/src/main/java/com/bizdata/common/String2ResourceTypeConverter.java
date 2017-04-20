package com.bizdata.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 资源类型字符串转换为对应资源类型枚举
 * 注意后面泛型为<String,ResourceType>,表示只有String转换为ResourceType枚举类型时,才会调用该转换！
 *
 * @author sdevil507
 * @version 1.0
 */
public class String2ResourceTypeConverter implements Converter<String, ResourceType> {

    @Override
    public ResourceType convert(String source) {
        String value = source.trim().toUpperCase();
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return ResourceType.valueOf(value);
    }
}
