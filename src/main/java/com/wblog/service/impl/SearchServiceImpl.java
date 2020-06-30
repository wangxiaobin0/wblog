package com.wblog.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wblog.dao.ArticleDao;
import com.wblog.model.to.ArticleEsTo;
import com.wblog.model.vo.ArticleItemVo;
import com.wblog.model.vo.SearchParamVo;
import com.wblog.model.vo.SearchResultVo;
import com.wblog.service.SearchService;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 文章索引
     */
    public static final String ARTICLE_INDEX = "article";

    /**
     * 搜索字段：标题
     */
    public static final String SEARCH_FIELD_TITLE = "title";

    /**
     * 搜索字段：正文
     */
    public static final String SEARCH_FIELD_CONTENT = "content";

    /**
     * 搜索/聚合字段：标签
     */
    public static final String SEARCH_FIELD_TAG_NAME = "tags.name.keyword";

    /**
     * 聚合字段：标签ID
     */
    public static final String SEARCH_FIELD_TAG_ID = "tags.id";

    /**
     * nested聚合id
     */
    public static final String SEARCH_AGG_NESTED_TAG = "tagAgg";

    public static final String SEARCH_AGG_NESTED_PATH = "tags";

    /**
     * tagId聚合id
     */
    public static final String SEARCH_AGG_NESTED_TAG_ID = "tagIdAgg";

    /**
     * tagName聚合id
     */
    public static final String SEARCH_AGG_NESTED_TAG_NAME = "tagNameAgg";
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
        IndexRequest indexRequest = new IndexRequest(ARTICLE_INDEX);
        //设置ES中的docId
        indexRequest.id(articleItem.getId().toString());
        indexRequest.source(objectMapper.writeValueAsString(articleEsTo), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(indexResponse);
        RestStatus status = indexResponse.status();
        return null;
    }

    @Override
    public SearchResultVo search(SearchParamVo searchParam) throws IOException {
        //构造搜索条件
        SearchRequest searchRequest = getSearchRequest(searchParam);
        //搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchResultVo searchResult = getSearchResult(searchResponse);
        searchResult.setSearchParam(searchParam);
        return searchResult;
    }

    @Override
    public Boolean delete(Long id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(ARTICLE_INDEX);
        //设置docId
        deleteRequest.id(id.toString());
        restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return null;
    }

    /**
     * 构造搜索条件
     *
     * @param searchParam 搜索关键字
     * @return
     */
    private SearchRequest getSearchRequest(SearchParamVo searchParam) {
        SearchRequest searchRequest = new SearchRequest();



        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        //多字段查询
        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(
                searchParam.getKey(),
                SEARCH_FIELD_TITLE,
                SEARCH_FIELD_CONTENT,
                SEARCH_FIELD_TAG_NAME);
        boolQuery.must(multiMatchQuery);
        //标签id不为空就添加标签id过滤
        if (searchParam.getTagId() != null) {
            TermQueryBuilder queryBuilder = QueryBuilders.termQuery(SEARCH_FIELD_TAG_ID, searchParam.getTagId());
            NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(SEARCH_AGG_NESTED_PATH, queryBuilder, ScoreMode.None);
            boolQuery.filter(nestedQuery);
        }

        //标签聚合
        NestedAggregationBuilder tagAgg = AggregationBuilders.nested(SEARCH_AGG_NESTED_TAG, SEARCH_AGG_NESTED_PATH);
        TermsAggregationBuilder tagIdAgg = AggregationBuilders.terms(SEARCH_AGG_NESTED_TAG_ID).field(SEARCH_FIELD_TAG_ID);
        TermsAggregationBuilder tagNameAgg = AggregationBuilders.terms(SEARCH_AGG_NESTED_TAG_NAME).field(SEARCH_FIELD_TAG_NAME);
        tagIdAgg.subAggregation(tagNameAgg);
        tagAgg.subAggregation(tagIdAgg);

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(SEARCH_FIELD_CONTENT);
        highlightBuilder.preTags("<b style='color:red'>");
        highlightBuilder.postTags("</b>");

        SearchSourceBuilder requestBuilder = new SearchSourceBuilder();
        requestBuilder.query(boolQuery);
        requestBuilder.highlighter(highlightBuilder);
        requestBuilder.aggregation(tagAgg);
        searchRequest.source(requestBuilder);
        return searchRequest;
    }

    /**
     * 构造搜索结果
     *
     * @param searchResponse
     * @return
     */
    private SearchResultVo getSearchResult(SearchResponse searchResponse) {
        //花费时间
        String took = searchResponse.getTook().toString();
        //命中数量
        long value = searchResponse.getHits().getTotalHits().value;

        SearchHit[] hits = searchResponse.getHits().getHits();
        //文章集合
        List<ArticleEsTo> articles = Arrays.stream(hits).map(hit -> {
            String data = hit.getSourceAsString();
            try {
                ArticleEsTo articleEsTo = objectMapper.readValue(data, ArticleEsTo.class);
                String content = articleEsTo.getContent();
                articleEsTo.setContent(content.substring(0, Math.min(content.length(), 160)) + "...");
                //TODO:高亮
                hit.getHighlightFields();
                return articleEsTo;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());

        //nestedAgg
        ParsedNested tagAgg = searchResponse.getAggregations().get(SEARCH_AGG_NESTED_TAG);
        //tagIdAgg
        ParsedLongTerms tagIdAgg = tagAgg.getAggregations().get(SEARCH_AGG_NESTED_TAG_ID);
        List<? extends Terms.Bucket> buckets = tagIdAgg.getBuckets();
        List<SearchResultVo.TagSearchVo> tags = buckets.stream().map(bucket -> {
            //文档数量
            long docCount = bucket.getDocCount();
            //标签ID
            Long tagId = bucket.getKeyAsNumber().longValue();
            //tagNameAgg
            ParsedStringTerms tagNameAgg = bucket.getAggregations().get(SEARCH_AGG_NESTED_TAG_NAME);
            //标签名
            String tagName = tagNameAgg.getBuckets().get(0).getKeyAsString();
            SearchResultVo.TagSearchVo tag = new SearchResultVo.TagSearchVo(tagId, tagName, docCount);
            return tag;
        }).collect(Collectors.toList());
        SearchResultVo resultVo = new SearchResultVo();
        resultVo.setTook(took);
        resultVo.setTotalHits(value);
        resultVo.setArticles(articles);
        resultVo.setTags(tags);
        return resultVo;
    }
}
