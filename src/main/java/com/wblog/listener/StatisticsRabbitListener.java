package com.wblog.listener;

import com.rabbitmq.client.Channel;
import com.wblog.annotation.Rabbit;
import com.wblog.common.constant.MQConstant;
import com.wblog.model.to.MessageTo;
import com.wblog.service.StatisticsService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@RabbitListener(queues = {MQConstant.StatisticsConstant.STATISTICS_INITIALIZE_QUEUE})
public class StatisticsRabbitListener {

    @Autowired
    StatisticsService statisticsService;

    @Rabbit
    @RabbitHandler
    public void initialize(MessageTo messageTo, Message message, Channel channel) {
        statisticsService.initialize();
    }
}
