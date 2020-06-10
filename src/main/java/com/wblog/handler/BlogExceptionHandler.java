package com.wblog.handler;

import com.wblog.common.utils.R;
import com.wblog.exception.AuthException;
import com.wblog.exception.PolicyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class BlogExceptionHandler {
    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public R handleAuthException(RuntimeException e) {
        return R.error(500, e.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(PolicyException.class)
    public R handlePolicyException(PolicyException e) {
        return R.error(500, e.getMessage());
    }
}
