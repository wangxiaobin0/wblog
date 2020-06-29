package com.wblog.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserViewVo implements Serializable {
    private List<ArticleIndexVo> articleList;
    private String tagJson;
}
