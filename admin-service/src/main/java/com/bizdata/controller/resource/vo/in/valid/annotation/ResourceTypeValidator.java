package com.bizdata.controller.resource.vo.in.valid.annotation;

import com.bizdata.common.ResourceType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用于ResourceType类型校验
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
public class ResourceTypeValidator implements ConstraintValidator<ResourceTypeValidAnnotation, ResourceType> {


    @Override
    public void initialize(ResourceTypeValidAnnotation constraintAnnotation) {

    }

    @Override
    public boolean isValid(ResourceType value, ConstraintValidatorContext context) {
        if (null == value) {
            return false;
        }
        return true;
    }
}
