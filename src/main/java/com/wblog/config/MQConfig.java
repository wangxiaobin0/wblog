package com.wblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wblog.common.enume.MqMessageFailEnum;
import com.wblog.model.entity.MqFailMessageEntity;
import com.wblog.service.MqFailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * 消息队列配置
 */
@Slf4j
@Configuration
public class MQConfig {

    @Autowired
    MqFailMessageService mqFailMessageService;

    @Bean
    public RabbitTemplate rabbitTemplate(RabbitProperties properties,
                                         ObjectProvider<MessageConverter> messageConverter,
                                         ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers,
                                         ConnectionFactory connectionFactory) {
        PropertyMapper map = PropertyMapper.get();
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        messageConverter.ifUnique(template::setMessageConverter);
        template.setMandatory(determineMandatoryFlag(properties));
        RabbitProperties.Template templateProperties = properties.getTemplate();
        map.from(templateProperties::getReceiveTimeout).whenNonNull().as(Duration::toMillis)
                .to(template::setReceiveTimeout);
        map.from(templateProperties::getReplyTimeout).whenNonNull().as(Duration::toMillis)
                .to(template::setReplyTimeout);
        map.from(templateProperties::getExchange).to(template::setExchange);
        map.from(templateProperties::getRoutingKey).to(template::setRoutingKey);
        map.from(templateProperties::getDefaultReceiveQueue).whenNonNull().to(template::setDefaultReceiveQueue);
        template.setConfirmCallback(((correlationData, ack, cause) -> {
            log.info("生产者发布消息:{}\nack模式:{}\n发布失败原因{}", correlationData, ack, cause);
            if (cause != null) {
                MqFailMessageEntity failMessageEntity = new MqFailMessageEntity();
                failMessageEntity.setMessage(new String(correlationData.getReturnedMessage().getBody()));
                failMessageEntity.setFailReason(cause);
                failMessageEntity.setState(MqMessageFailEnum.TO_MQ.getCode());
                mqFailMessageService.save(failMessageEntity);
            }
        }));
        /**
         * 交换器发送消息到队列时触发
         */
        template.setReturnCallback(((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("\n消息:{}投递失败\n响应码:{}\n响应信息:{}\n目标交换器:{}\n路由键:{}",
                    message, replyCode, replyText, exchange, routingKey);
            MqFailMessageEntity failMessageEntity = new MqFailMessageEntity();
            failMessageEntity.setExchange(exchange);
            failMessageEntity.setRoutingKey(routingKey);
            failMessageEntity.setMessage(new String(message.getBody()));
            failMessageEntity.setFailReason(replyText);
            failMessageEntity.setState(MqMessageFailEnum.TO_QUEUE.getCode());
            mqFailMessageService.save(failMessageEntity);
        }));
        return template;
    }

    private boolean determineMandatoryFlag(RabbitProperties properties) {
        Boolean mandatory = properties.getTemplate().getMandatory();
        return (mandatory != null) ? mandatory : properties.isPublisherReturns();
    }

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(objectMapper);
        messageConverter.setClassMapper(classMapper());
        return messageConverter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        return classMapper;
    }
}
