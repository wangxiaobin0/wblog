package com.wblog.handler;

import com.wblog.common.utils.R;
import com.wblog.exception.AuthException;
import com.wblog.exception.PolicyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BlogExceptionHandler {
    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public R handleAuthException(AuthException e) {
        return R.error(500, e.getMessage());
    }


    @ExceptionHandler(SocketTimeoutException.class)
    public String handleSocketTimeoutException(SocketTimeoutException e, HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("errorCode", "500");
        error.put("errorMessage", "连接超时");
        //添加自定义消息
        request.setAttribute("data", error);
        //添加错误码
        request.setAttribute("javax.servlet.error.status_code", 500);
        return "forward:/error";
    }


    @ResponseBody
    @ExceptionHandler(PolicyException.class)
    public R handlePolicyException(PolicyException e) {
        return R.error(500, e.getMessage());
    }
}
