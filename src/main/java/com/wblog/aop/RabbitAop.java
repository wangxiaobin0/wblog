package com.wblog.aop;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Aspect
@Component
public class RabbitAop {

    @Pointcut("@annotation(com.wblog.annotation.Rabbit)")
    public void pointcut() {};

    @AfterReturning(pointcut = "pointcut()")
    public void afterReturn(JoinPoint joinPoint) throws IOException {
        Message message = getMessage(joinPoint);
        Channel channel = getChannel(joinPoint);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "t")
    public void AfterException(JoinPoint joinPoint, Throwable t) throws IOException {
        Message message = getMessage(joinPoint);
        Channel channel = getChannel(joinPoint);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
    }

    private Message getMessage(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Message ) {
                return (Message) arg;
            }
        }
        return null;
    }

    private Channel getChannel(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Channel ) {
                return (Channel) arg;
            }
        }
        return null;
    }
}
