package com.ustc.exception;

/**
 * @author 叶嘉耘
 * @date 2021-3-1
 */

public enum ServiceExceptionEnum {
    /**
     * 第1位, 异常类型:
     *      1 - 业务级别异常
     *      2 - 系统级别异常
     * 第2-4位, 异常模块:
     *      001 - 上传模块
     *      002 - 下载模块
     *      003 - 用户模块
     *      004 - 工具类
     * 第5-7位, 错误码:
     */
    SUCCESS(0, "成功"),
    SYSTEM_ERROR(2001000, "服务端发生异常"),
    // 上传模块
    CHUNK_NOT_NULL(1001000,"切块不能为空"),
    // 下载模块
    // 用户模块
    // 工具类
    VALIDATE_ERROR(2004000, "参数校验异常")
    ;

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误提示信息
     */
    private String message;

    ServiceExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public ServiceExceptionEnum setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ServiceExceptionEnum setMessage(String message) {
        this.message = message;
        return this;
    }
}
