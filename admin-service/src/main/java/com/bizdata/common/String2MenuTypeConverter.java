package com.bizdata.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 字符串转换为对应菜单类型枚举
 *
 * Created by sdevil507 on 2017/5/5.
 */
public class String2MenuTypeConverter implements Converter<String,MenuType> {
    @Override
    public MenuType convert(String source) {
        String value = source.trim().toUpperCase();
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return MenuType.valueOf(value);
    }
}
