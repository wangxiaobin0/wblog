package com.wblog.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wblog.common.constant.SearchConstant;
import com.wblog.dao.ArticleDao;
import com.wblog.model.to.ArticleEsTo;
import com.wblog.model.vo.ArticleItemVo;
import com.wblog.service.SearchService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class SearchServiceImpl implements SearchService {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    @Resource
    ArticleDao articleDao;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public Boolean add(Long id) throws IOException {
        ArticleEsTo articleEsTo = new ArticleEsTo();

        //复用获取文章详情的查询
        ArticleItemVo articleItem = articleDao.getArticleItem(id);
        BeanUtils.copyProperties(articleItem, articleEsTo);

        articleEsTo.setContent(articleItem.getHtml().replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", ""));
        IndexRequest indexRequest = new IndexRequest(SearchConstant.ARTICLE_INDEX);
        indexRequest.source(objectMapper.writeValueAsString(articleEsTo), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
        RestStatus status = indexResponse.status();
        return null;
    }
}
