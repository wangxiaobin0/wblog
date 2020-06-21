package com.wblog.task;

import com.wblog.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MqTask {

    @Autowired
    RabbitMqService rabbitMqService;

    /**
     * 每隔一小时执行一次任务
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void reSendFailMessage(){
        rabbitMqService.reSend();
    }
}
