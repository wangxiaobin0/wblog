package com.wblog.listener;

import com.rabbitmq.client.Channel;
import com.wblog.common.constant.MQConstant;
import com.wblog.model.to.ArticleMQTo;
import com.wblog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Slf4j
@RabbitListener(queues = {MQConstant.ArticleConstant.ARTICLE_DRAFT_DEAD_QUEUE})
@Configuration
public class ArticleRabbitListener {

    @Autowired
    ArticleService articleService;

    @RabbitHandler
    public void deleteExpireArticle(ArticleMQTo articleMQTo, Message message, Channel channel) throws IOException {
        log.info("删除过期文章:{}流程开始", articleMQTo.getId());
        Boolean b = articleService.deleteExpireArticle(articleMQTo);
        if (b) {
            //更新成功，回复ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("删除过期文章:{}成功", articleMQTo.getId());
        } else {
            //更新失败，消息重新入队
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            log.info("删除过期文章:{}失败", articleMQTo.getId());
        }
    }
}
