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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BlogExceptionHandler {
    @ExceptionHandler({AuthException.class, PolicyException.class})
    @ResponseBody
    public R handleAuthException(AuthException e) {
        return R.error(500, e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(SQLException e, HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("errorCode", "500");
        error.put("errorMessage", "数据库操作异常");
        //添加自定义消息
        request.setAttribute("data", error);
        //添加错误码
        request.setAttribute("javax.servlet.error.status_code", 500);
        return "forward:/error";
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
    @ExceptionHandler(RuntimeException.class)
    public String handleSRuntimeException(RuntimeException e, HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("errorCode", "500");
        error.put("errorMessage", "服务器处理异常");
        //添加自定义消息
        request.setAttribute("data", error);
        //添加错误码
        request.setAttribute("javax.servlet.error.status_code", 500);
        return "forward:/error";
    }
}
