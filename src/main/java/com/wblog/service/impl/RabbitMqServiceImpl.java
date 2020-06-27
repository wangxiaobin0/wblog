package com.wblog.service.impl;

import com.wblog.common.enume.MqMessageFailEnum;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.model.entity.MqFailMessageEntity;
import com.wblog.model.entity.MqMessageEntity;
import com.wblog.service.MqFailMessageService;
import com.wblog.service.MqMessageService;
import com.wblog.service.RabbitMqService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RabbitMqServiceImpl implements RabbitMqService {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MqMessageService mqMessageService;

    @Autowired
    MqFailMessageService failMessageService;

    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(String exchange, String routingKey, Object object) {
        //保存发送的消息
        MqMessageEntity mqMessage = new MqMessageEntity();
        mqMessage.setAdminId(AdminRequestInterceptor.getAdmin().getAdminId());
        mqMessage.setMessage(object.toString());
        mqMessageService.save(mqMessage);
        //发送消息
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }

    @Override
    public void reSend() {
        List<MqFailMessageEntity> list = failMessageService.list();
        for (MqFailMessageEntity entity : list) {
            //失败类型为投递到队列失败
            if (entity.getState() == MqMessageFailEnum.TO_QUEUE.getCode()) {
                rabbitTemplate.convertAndSend(entity.getExchange(), entity.getRoutingKey(), entity.getMessage());
                //发送后把这条失败记录删除，即使发送也会有新的失败记录
                failMessageService.removeById(entity);
            } else {
                //TODO:发送到mq失败重发
            }
        }
    }
}
