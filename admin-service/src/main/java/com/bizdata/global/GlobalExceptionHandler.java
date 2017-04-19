package com.bizdata.global;

import com.bizdata.result.ResultStateUtil;
import com.bizdata.result.ResultStateVO;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获统一处理
 *
 * @author sdevil507
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理@valid注解标记参数验证错误异常
     *
     * @param ex BindException
     * @return json类型提示
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultStateVO processValidationError(BindException ex) {
        BindingResult result = ex.getBindingResult();
        return ResultStateUtil.create(-1001, result.getFieldError().getField() + "[" + result.getFieldError().getDefaultMessage() + "]");
    }
}
