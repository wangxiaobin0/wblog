package com.wblog.aop;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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

    @Around("pointcut()")
    public void round(ProceedingJoinPoint pjp) throws IOException {
        Message message = getMessage(pjp);
        Channel channel = getChannel(pjp);
        try {
            Object proceed = pjp.proceed();
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Throwable t) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
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
