package com.ustc.mvc;

import com.ustc.exception.ServiceException;
import com.ustc.utils.CommonResult;
import com.ustc.utils.CommonResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * @author 叶嘉耘
 */
 @ControllerAdvice(basePackages = {
         "com.ustc.upload.controller",
         "com.ustc.login.controller",
         "com.ustc.download.controller"})
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 处理ServiceException
     *
     * @param req HTTP请求
     * @param ex  异常
     * @return 封装好的异常
     */
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public CommonResult serviceExceptionHandler(HttpServletRequest req, ServiceException ex) {
        // 记录异常
        logger.debug("[serviceExceptionHandler]", ex);
        // 包装为 CommonResult结果
        return CommonResultUtils.error(ex.getCode(), ex.getMessage());
    }

    /**
     * SpringMVC参数不正确
     *
     * @param req HTTP请求
     * @param ex  异常
     * @return 封装好的异常
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult missingServletRequestParameterExceptionHandler(HttpServletRequest req, ServiceException ex) {
        logger.debug("[missingServletRequestParameterExceptionHandler]", ex);
        return CommonResultUtils.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理其他异常
     *
     * @param req HTTP请求
     * @param ex  异常
     * @return 封装好的异常
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(HttpServletRequest req, Exception ex) {
        logger.error("[exceptionHandler]", ex);
        // 代补充
        return CommonResultUtils.error(500, ex.getMessage());
    }
}
