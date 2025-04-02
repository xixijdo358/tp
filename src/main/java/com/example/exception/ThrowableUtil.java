package com.example.exception;

import org.hibernate.exception.ConstraintViolationException;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableUtil {

    /**
     * 获取堆栈信息
     * 该方法用于获取异常的堆栈信息，并将其转换为字符串格式。
     * @param throwable 需要获取堆栈信息的异常对象
     * @return 返回异常的堆栈信息字符串
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw); // 将异常的堆栈信息写入PrintWriter
            return sw.toString(); // 返回堆栈信息字符串
        }
    }

    /**
     * 删除异常
     * 该方法用于处理外键约束异常，如果捕获到外键约束异常，则抛出BadRequestException。
     * @param e 需要处理的异常
     * @param msg 错误消息
     */
    public static void throwForeignKeyException(Throwable e, String msg){
        Throwable t = e.getCause(); // 获取异常的根本原因
        while ((t != null) && !(t instanceof ConstraintViolationException)) {
            t = t.getCause(); // 递归查找根本原因
        }
        if (t != null) {
            throw new BadRequestException(msg); // 如果找到外键约束异常，抛出BadRequestException
        }
        assert false; // 断言，确保不会到达此处
        throw new BadRequestException("删除失败：" + t.getMessage()); // 抛出删除失败的BadRequestException
    }

}
