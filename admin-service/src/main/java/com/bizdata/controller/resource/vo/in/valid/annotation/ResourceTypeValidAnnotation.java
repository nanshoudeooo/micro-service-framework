package com.bizdata.controller.resource.vo.in.valid.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 用于资源类型参数校验
 * <p>
 * Created by sdevil507 on 2017/4/13.
 */
@Documented
@Constraint(validatedBy = {ResourceTypeValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceTypeValidAnnotation {

    String message() default "资源类型参数必须提交!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
