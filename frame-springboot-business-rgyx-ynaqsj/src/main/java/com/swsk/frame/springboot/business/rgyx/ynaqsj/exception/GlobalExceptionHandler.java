package com.swsk.frame.springboot.business.rgyx.ynaqsj.exception;

import com.swsk.frame.springboot.business.rgyx.ynaqsj.util.ResultUtil;
import com.swsk.frame.springboot.core.util.MResult;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * 捕获@Valid注解校验的异常信息
 *
 * @author huangyongqi
 * @date 2023/4/7
 */

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public MResult bindExceptionHandler(BindException e) {
        String message =
                e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return ResultUtil.error(message);
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public MResult constraintViolationExceptionHandler(ConstraintViolationException e) {
        String message =
                e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return ResultUtil.error(message);
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public MResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message =
                e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return ResultUtil.error(message);
    }

}
