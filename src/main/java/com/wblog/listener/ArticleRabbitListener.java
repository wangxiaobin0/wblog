package com.wblog.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.wblog.annotation.Rabbit;
import com.wblog.annotation.SysLog;
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

    public final ObjectMapper objectMapper = new ObjectMapper();

    @SysLog(business = "删除过期文章")
    @Rabbit
    @RabbitHandler
    public void deleteExpireArticle(ArticleMQTo articleMQTo, Message message, Channel channel) throws IOException {
        articleService.deleteExpireArticle(articleMQTo);
    }
    @SysLog(business = "删除过期文章")
    @Rabbit
    @RabbitHandler
    public void deleteExpireArticleByReSend(String msg, Message message, Channel channel) throws IOException {
        ArticleMQTo articleMQTo = objectMapper.readValue(msg, ArticleMQTo.class);
        articleService.deleteExpireArticle(articleMQTo);
    }
}
