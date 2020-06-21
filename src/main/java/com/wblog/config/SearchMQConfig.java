package com.wblog.config;

import com.wblog.common.constant.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 搜索的MQ配置
 */
@Configuration
public class SearchMQConfig {
    /**
     * 搜索交换器
     *
     * @return
     */
    @Bean
    public Exchange searchEventExchange() {
        Exchange exchange = new TopicExchange(MQConstant.SearchConstant.SEARCH_EVENT_EXCHANGE, true, false);
        return exchange;
    }

    /**
     * 添加文章消息队列
     *
     * @return
     */
    @Bean
    public Queue addArticleQueue() {
        Queue queue = new Queue(MQConstant.SearchConstant.SEARCH_ADD_ARTICLE_QUEUE, true, false, false);
        return queue;
    }

    /**
     * 添加文章binding
     * @return
     */
    @Bean
    public Binding addArticleBinding() {
        Binding binding = new Binding(
                MQConstant.SearchConstant.SEARCH_ADD_ARTICLE_QUEUE,
                Binding.DestinationType.QUEUE,
                MQConstant.SearchConstant.SEARCH_EVENT_EXCHANGE,
                MQConstant.SearchConstant.SEARCH_ADD_ARTICLE_ROUTING_KEY,
                null);
        return binding;
    }

    /**
     * 删除文章消息队列
     *
     * @return
     */
    @Bean
    public Queue deleteArticleQueue() {
        Queue queue = new Queue(MQConstant.SearchConstant.SEARCH_DELETE_ARTICLE_QUEUE, true, false, false);
        return queue;
    }

    /**
     * 删除文章binding
     * @return
     */
    @Bean
    public Binding deleteArticleBinding() {
        Binding binding = new Binding(
                MQConstant.SearchConstant.SEARCH_DELETE_ARTICLE_QUEUE,
                Binding.DestinationType.QUEUE,
                MQConstant.SearchConstant.SEARCH_EVENT_EXCHANGE,
                MQConstant.SearchConstant.SEARCH_DELETE_ARTICLE_ROUTING_KEY,
                null);
        return binding;
    }
}