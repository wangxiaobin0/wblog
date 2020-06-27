package com.wblog.model.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文章mqTo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleMQTo implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 状态.
     *  1:新增 发送添加到ES
     *  3:草稿箱 发送延时队列做定期删除
     *  4:回收站 发送延时队列做定期删除
     */
    private Integer state;
}
