package com.ustc.utils;

import com.ustc.exception.ServiceException;
import com.ustc.exception.ServiceExceptionEnum;

/**
 * @author 叶嘉耘
 */
public class ValidateUtils {
    private final static String NULL = "null";

    public static void validate(String name, String msg) {
        if (name == null || "".equals(name) || NULL.equals(name)) {
            throw new ServiceException(ServiceExceptionEnum.VALIDATE_ERROR.setMessage(msg + "不能为空"));
        }
    }

    public static void validate(Long name, String msg) {
        if (name == null) {
            throw new ServiceException(ServiceExceptionEnum.VALIDATE_ERROR.setMessage(msg + "不能为空"));
        }
    }

    public static void validate(Integer name, String msg) {
        if (name == null) {
            throw new ServiceException(ServiceExceptionEnum.VALIDATE_ERROR.setMessage(msg + "不能为空"));
        }
    }
}
