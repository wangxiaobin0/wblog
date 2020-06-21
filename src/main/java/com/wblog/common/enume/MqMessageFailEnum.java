package com.wblog.common.enume;

import lombok.Getter;

/**
 * 消息发送失败
 */
public enum MqMessageFailEnum {
    TO_MQ(1, "生产者发送到MQ失败"),
    TO_QUEUE(2, "交换器发送到队列失败");

    @Getter
    private Integer code;
    @Getter
    private String msg;
    MqMessageFailEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
