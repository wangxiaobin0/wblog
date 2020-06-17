package com.wblog.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.model.entity.MqFailMessageEntity;
import com.wblog.model.entity.MqMessageEntity;
import com.wblog.service.MqFailMessageService;
import com.wblog.service.MqMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 消息队列配置
 */
@Slf4j
@Configuration
public class MQConfig {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MqMessageService mqMessageService;

    @Autowired
    MqFailMessageService mqFailMessageService;

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    void init() {

        /**
         * publisher发布一条消息就会触发，无论成功失败
         */
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) -> {
            log.info("生产者发布消息:{}\nack模式:{}\n发布失败原因{}", correlationData, ack, cause);

            try {
                //TODO:保存消息到数据库
                MqMessageEntity messageEntity = new MqMessageEntity();
                messageEntity.setAdminId(AdminRequestInterceptor.getAdmin().getAdminId());
                String s = objectMapper.writeValueAsString(correlationData.getReturnedMessage().getMessageProperties());
                messageEntity.setMessage(s);
                //mqMessageService.save(messageEntity);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (cause != null) {
                MqFailMessageEntity failMessageEntity = new MqFailMessageEntity();
                failMessageEntity.setAdminId(AdminRequestInterceptor.getAdmin().getAdminId());
                failMessageEntity.setMessage("");
                failMessageEntity.setState(1);
                //mqFailMessageService.save(failMessageEntity);
            }
        }));
        /**
         * 交换器发送消息到队列时触发
         */
        rabbitTemplate.setReturnCallback(((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("\n消息:{}投递失败\n响应码:{}\n响应信息:{}\n目标交换器:{}\n路由键:{}",
                    message.getMessageProperties().getDeliveryTag(), replyCode, replyText, exchange, routingKey);
            //TODO:保存发送失败的消息到数据库
            MqFailMessageEntity failMessageEntity = new MqFailMessageEntity();
            failMessageEntity.setAdminId(AdminRequestInterceptor.getAdmin().getAdminId());
            failMessageEntity.setState(1);
            //mqFailMessageService.save(failMessageEntity);
        }));
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
