package com.example.exception;

import com.example.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**功能描述：统一异常处理类
 * 该类用于处理全局异常，特别是BadRequestException异常。
 * 通过使用@ControllerAdvice注解，Spring会自动扫描并处理控制器中的异常。
 */
@ControllerAdvice // 声明该类为全局异常处理类
@Slf4j // 使用Slf4j记录日志
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class) // 指定处理BadRequestException异常
    @ResponseBody // 返回响应体
    public BaseResult error(BadRequestException e){
        e.printStackTrace(); // 打印异常堆栈信息
        log.error(ThrowableUtil.getStackTrace(e)); // 记录异常日志
        return BaseResult.fail(e.getMessage(), e.getStatus()); // 返回失败的BaseResult对象，包含错误消息和状态码
    }
}
