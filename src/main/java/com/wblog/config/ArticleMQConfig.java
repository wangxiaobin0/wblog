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

@Configuration
public class ArticleMQConfig {

    /**
     * 文章事件交换器
     *
     * @return
     */
    @Bean
    public Exchange articleEventExchange() {
        Exchange exchange = new TopicExchange(MQConstant.ArticleConstant.ARTICLE_EVENT_EXCHANGE, true, false);
        return exchange;
    }

    /**
     * 草稿箱文章延时队列，无消费者
     *
     * @return
     */
    @Bean
    public Queue articleDraftCreateQueue() {
        //延时队列参数
        Map<String, Object> params = new HashMap<>();
        params.put(MQConstant.MQ_DEAD_LETTER_EXCHANGE, MQConstant.ArticleConstant.ARTICLE_EVENT_EXCHANGE);
        params.put(MQConstant.MQ_DEAD_LETTER_ROUTING_KEY, MQConstant.ArticleConstant.ARTICLE_DRAFT_DEAD_ROUTING_KEY);
        params.put(MQConstant.MQ_DEAD_LETTER_TTL, 20000);
        Queue queue = new Queue(MQConstant.ArticleConstant.ARTICLE_DRAFT_DELAY_QUEUE, true, false, false, params);
        return queue;
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue articleDraftDeleteQueue() {
        Queue queue = new Queue(MQConstant.ArticleConstant.ARTICLE_DRAFT_DEAD_QUEUE, true, false, false);
        return queue;
    }

    /**
     * 延时队列与交换器的绑定
     *
     * @return
     */
    @Bean
    public Binding bindDelayQueue() {
        Binding binding = new Binding(
                MQConstant.ArticleConstant.ARTICLE_DRAFT_DELAY_QUEUE,
                Binding.DestinationType.QUEUE,
                MQConstant.ArticleConstant.ARTICLE_EVENT_EXCHANGE,
                MQConstant.ArticleConstant.ARTICLE_DRAFT_DELAY_ROUTING_KEY,
                null
                );
        return binding;
    }
    /**
     * 死信队列与交换器的绑定
     *
     * @return
     */
    @Bean
    public Binding bindDeadLetterQueue() {
        Binding binding = new Binding(
                MQConstant.ArticleConstant.ARTICLE_DRAFT_DEAD_QUEUE,
                Binding.DestinationType.QUEUE,
                MQConstant.ArticleConstant.ARTICLE_EVENT_EXCHANGE,
                MQConstant.ArticleConstant.ARTICLE_DRAFT_DEAD_ROUTING_KEY,
                null
        );
        return binding;
    }
}
