package com.wblog.service;

public interface RabbitMqService {
    void sendMessage(String exchange, String routingKey, Object object);

    void reSend();
}
