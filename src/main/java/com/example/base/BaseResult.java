package com.example.base;

import lombok.Data;

import java.io.Serializable;

/**功能描述：返回结果集
 * 该类用于封装API的返回结果，包括状态码、消息、数据和时间戳等信息。
 */
@Data
public class BaseResult implements Serializable {

    public static final int STATUS_SUCCESS = 200; // 成功状态码
    public static final int STATUS_FALL = 500;   // 失败状态码

    /**
     * 返回状态码
     */
    private int status;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object result;

    /**
     * 返回时间
     */
    private long timestamp = System.currentTimeMillis(); // 初始化时间戳为当前时间

    /**
     * 创建返回对象
     *
     * @param status 返回状态
     * @param message 返回消息
     * @param result  返回数据
     * @return 返回构建好的BaseResult对象
     */
    private static BaseResult createResult(int status, String message, Object result) {
        BaseResult baseResult = new BaseResult(); // 创建BaseResult实例
        baseResult.setStatus(status); // 设置状态码
        baseResult.setMessage(message); // 设置返回消息
        baseResult.setResult(result); // 设置返回数据
        return baseResult; // 返回构建好的结果对象
    }

    /**
     * 默认返回成功方法
     *
     * @return 返回成功的BaseResult对象
     */
    public static BaseResult success() {
        return BaseResult.createResult(STATUS_SUCCESS, "成功", null); // 返回成功状态
    }

    /**
     * 返回成功带消息
     *
     * @param message 返回消息
     * @return 返回成功的BaseResult对象
     */
    public static BaseResult success(String message) {
        return BaseResult.createResult(STATUS_SUCCESS, message, null); // 返回成功状态和消息
    }

    /**
     * 返回成功带数据
     *
     * @param result 返回数据
     * @return 返回成功的BaseResult对象
     */
    public static BaseResult success(Object result) {
        return BaseResult.createResult(STATUS_SUCCESS, "成功", result); // 返回成功状态和数据
    }

    /**
     * 返回成功带消息和数据
     *
     * @param message 返回消息
     * @param result 返回数据
     * @return 返回成功的BaseResult对象
     */
    public static BaseResult success(String message, Object result) {
        return BaseResult.createResult(STATUS_SUCCESS, message, result); // 返回成功状态、消息和数据
    }

    /**
     * 默认返回失败方法
     *
     * @return 返回失败的BaseResult对象
     */
    public static BaseResult fail() {
        return BaseResult.createResult(STATUS_FALL, "失败", null); // 返回失败状态
    }

    /**
     * 返回失败带消息
     *
     * @param message 返回消息
     * @return 返回失败的BaseResult对象
     */
    public static BaseResult fail(String message) {
        return BaseResult.createResult(STATUS_FALL, message, null); // 返回失败状态和消息
    }

    /**
     * 返回失败带消息和数据
     *
     * @param message 返回消息
     * @param result 返回数据
     * @return 返回失败的BaseResult对象
     */
    public static BaseResult fail(String message, Object result) {
        return BaseResult.createResult(STATUS_FALL, message, result); // 返回失败状态、消息和数据
    }
}
