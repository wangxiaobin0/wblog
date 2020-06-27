package com.wblog.model.to;

import lombok.Data;

import java.io.Serializable;

/**
 * MQ消息to
 */
@Data
public class MessageTo implements Serializable {
    private Integer code;
    private String msg;
}
