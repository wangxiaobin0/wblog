package com.wblog.model.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 草稿箱文章to
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleMQTo {
    /**
     * id
     */
    private Long id;

    /**
     * 状态. 3:草稿箱；4:回收站
     */
    private Integer state;
}
