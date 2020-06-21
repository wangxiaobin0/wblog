package com.wblog.listener;

import com.rabbitmq.client.Channel;
import com.wblog.annotation.Rabbit;
import com.wblog.annotation.SysLog;
import com.wblog.common.constant.MQConstant;
import com.wblog.common.enume.ArticleMqEnum;
import com.wblog.model.to.ArticleMQTo;
import com.wblog.service.SearchService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RabbitListener(queues = {MQConstant.SearchConstant.SEARCH_ADD_ARTICLE_QUEUE, MQConstant.SearchConstant.SEARCH_DELETE_ARTICLE_QUEUE})
public class SearchRabbitListener {

    @Autowired
    SearchService searchService;

    /**
     * 添加文章监听器
     * @param articleMQTo
     * @param message 消息，给@Rabbit注解使用
     * @param channel 通道，给@Rabbit注解使用
     * @throws IOException
     */
    @SysLog(business = "添加文章到ElasticSearch")
    @Rabbit
    @RabbitHandler
    public void addArticle(ArticleMQTo articleMQTo, Message message, Channel channel) throws IOException {
        if (articleMQTo.getState() == ArticleMqEnum.PUBLIC.getCode()) {
            searchService.add(articleMQTo.getId());
        }
    }
}
