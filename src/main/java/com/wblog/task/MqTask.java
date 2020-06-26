package com.wblog.task;

import com.wblog.service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqTask {

    @Autowired
    RabbitMqService rabbitMqService;

    /**
     * 每隔一小时执行一次任务
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void reSendFailMessage(){
        log.info("定时任务:重新发送失败的消息");
        rabbitMqService.reSend();
    }
}
