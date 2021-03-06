package com.wblog.aop;

import com.wblog.annotation.SysLog;
import com.wblog.common.enume.LogStateEnum;
import com.wblog.common.utils.R;
import com.wblog.common.utils.ThreadLocalUtils;
import com.wblog.model.entity.SystemLogEntity;
import com.wblog.model.to.AdminTo;
import com.wblog.model.to.UserTo;
import com.wblog.service.SystemLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志aop
 *  由于最终通知与后置通知的执行顺序问题，最终采用环绕通知实现
 */
@Slf4j
@Aspect
@Component
public class SysLogAop {

    @Autowired
    SystemLogService systemLogService;

    /**
     * 切入点为@SysLog注解
     */
    @Pointcut("@annotation(com.wblog.annotation.SysLog)")
    public void pointCut() {
    }


    /**
     * 环绕通知
     * @param pjp
     * @return 返回值.必须有，不然所有请求都会返回null
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            //前置
            doBefore(pjp);
            Object proceed = pjp.proceed(pjp.getArgs());
            //后置
            doAfterReturning(pjp);
            return proceed;
        } catch (Throwable throwable) {
            //异常
            doAfterThrowing(throwable);
            throwable.printStackTrace();
            Annotation annotation = getAnnotation(pjp, ResponseBody.class);
            if (annotation != null) {
                return R.error();
            }
        } finally {
            //最终通知
            adAfter();
        }
        return null;
    }


    /**
     * 前置通知
     */
    public void doBefore(JoinPoint joinpoint) {
        SystemLogEntity logEntity = initialUserLogEntity(joinpoint);
        //保存logEntity到localhost
        ThreadLocalUtils.setUserLog(logEntity);
//        log.info(logEntity.getClassName() + "." + logEntity.getMethodName()+ "()流程开始");
//        log.info("参数列表：" + logEntity.getParameter());
    }

    /**
     * 后置通知
     * @param joinpoint
     */
    public void doAfterReturning(JoinPoint joinpoint) {
        SystemLogEntity logEntity = ThreadLocalUtils.getUserLog();
        logEntity.setState(LogStateEnum.SUCCESS.getCode());
    }

    /**
     * 异常通知
     * @param t
     */
    public void doAfterThrowing(Throwable t) {
        SystemLogEntity logEntity = ThreadLocalUtils.getUserLog();
        logEntity.setState(LogStateEnum.FAIL.getCode());
        logEntity.setStateMessage(t !=null && t.getCause() !=null ? t.getCause().getMessage() : "");
        log.error(logEntity.getClassName() + "." + logEntity.getMethodName()+ "()流程异常{}", t.getMessage());
    }

    /**
     * 最终通知
     */
    public void adAfter() {
        SystemLogEntity logEntity = ThreadLocalUtils.getUserLog();
        logEntity.setTime(new Date());
        systemLogService.save(logEntity);
        log.info(logEntity.getClassName() + "." + logEntity.getMethodName()+ "()日志保存成功");
        log.info(logEntity.getClassName() + "." + logEntity.getMethodName()+ "()流程结束");
    }

    /**
     * 获取SysLog注解
     *
     * @param joinPoint
     * @param clazz 注解class
     * @return
     */
    public Annotation getAnnotation(JoinPoint joinPoint, Class clazz) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method != null) {
            Annotation annotation = method.getAnnotation(clazz);
            return annotation;
        }
        return null;
    }


    /**
     * 根据请求信息初始化UserLogEntity
     * @param joinpoint
     * @return
     */
    public SystemLogEntity initialUserLogEntity(JoinPoint joinpoint) {
        //获取访问用户信息
        UserTo userTo = ThreadLocalUtils.getUserTo();
        //获取管理员信息
        AdminTo adminTo = ThreadLocalUtils.getAdminTo();

        //url
        Controller controller = (Controller) getAnnotation(joinpoint, Controller.class);
        String url = "";
        if (controller != null) {
            url = controller.value();
        }
        //类名
        String className = joinpoint.getTarget().getClass().getName();
        //方法名
        String methodName = joinpoint.getSignature().getName();
        //参数
        Object[] args = joinpoint.getArgs();
        String[] parameterNames = ((MethodSignature) joinpoint.getSignature()).getParameterNames();
        String parameter = formatParameter(parameterNames, args);
        //交易
        SysLog sysLog = (SysLog) getAnnotation(joinpoint, SysLog.class);
        String business = sysLog.business();

        SystemLogEntity logEntity = new SystemLogEntity();
        if (adminTo != null) {
            logEntity.setAdminId(adminTo.getAdminId());
        } else {
            logEntity.setUserKey(logEntity.getUserKey());
        }
        logEntity.setUrl(url);
        logEntity.setClassName(className);
        logEntity.setMethodName(methodName);
        logEntity.setParameter(parameter.substring(0, parameter.length() > 255 ? 255 : parameter.length()));
        logEntity.setBusiness(business);
        return logEntity;
    }

    private String formatParameter(String[] parameterNames, Object[] objs) {
        Map<String, Object> map = null;
        int len = parameterNames.length;
        if (len > 0) {
            map = new HashMap<>();
        }
        for (int i = 0; i < len; i++) {
            map.put(parameterNames[i], objs[i]);
        }
        return map == null ? "" : map.toString();
    }
}
