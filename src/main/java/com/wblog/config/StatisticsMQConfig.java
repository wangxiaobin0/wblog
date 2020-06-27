package com.wblog.config;

import com.wblog.common.constant.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 统计MQ配置
 */
@Configuration
public class StatisticsMQConfig {

    /**
     * 统计事件交换器
     *
     * @return
     */
    @Bean
    public Exchange statisticsEventExchange() {
        Exchange exchange = new TopicExchange(MQConstant.StatisticsConstant.STATISTICS_EVENT_EXCHANGE, true, false);
        return exchange;
    }

    /**
     * 初始化文章统计队列
     * @return
     */
    @Bean
    public Queue initializeArticleQueue() {
        Queue queue = new Queue(
                MQConstant.StatisticsConstant.STATISTICS_INITIALIZE_QUEUE,
                true,
                false,
                false
        );
        return queue;
    }

    /**
     * 绑定关系
     * @return
     */
    @Bean
    public Binding initializeArticleBinding() {
        Binding binding = new Binding(
                MQConstant.StatisticsConstant.STATISTICS_INITIALIZE_QUEUE,
                Binding.DestinationType.QUEUE,
                MQConstant.StatisticsConstant.STATISTICS_EVENT_EXCHANGE,
                MQConstant.StatisticsConstant.STATISTICS_INITIALIZE_ROUTING_KEY,
                null);
        return binding;
    }
}
