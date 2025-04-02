package com.example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**功能描述：统一异常处理
 * 该类用于处理HTTP请求中的错误，特别是400 Bad Request错误。
 * 通过扩展RuntimeException，BadRequestException可以在运行时抛出，便于异常处理机制捕获。
 */
@Getter
public class BadRequestException extends RuntimeException {
    private Integer status = BAD_REQUEST.value(); // 默认状态码为400

    /**
     * 构造函数，接受错误消息
     * @param msg 错误消息
     */
    public BadRequestException(String msg) {
        super(msg); // 调用父类构造函数，传递错误消息
    }

    /**
     * 构造函数，接受HTTP状态和错误消息
     * @param status HTTP状态
     * @param msg 错误消息
     */
    public BadRequestException(HttpStatus status, String msg) {
        super(msg); // 调用父类构造函数，传递错误消息
        this.status = status.value(); // 设置状态码为传入的HTTP状态码
    }
}
