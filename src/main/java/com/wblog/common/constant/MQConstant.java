package com.wblog.common.constant;

/**
 * 消息队列相关常量
 */
public class MQConstant {

    /**
     * 死信交换器
     */
    public static final String MQ_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

    /**
     * 死信路由键
     */
    public static final String MQ_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    /**
     * 死信过期时间
     */
    public static final String MQ_DEAD_LETTER_TTL = "x-message-ttl";

    /**
     * 文章MQ常量
     */
    public static class ArticleConstant {
        /**
         * 文章交换器
         */
        public static final String ARTICLE_EVENT_EXCHANGE = "article.event.exchange";

        /**
         * 草稿箱文章保存延时消息路由键
         */
        public static final String ARTICLE_DRAFT_DELAY_ROUTING_KEY = "article.draft.create.#";

        /**
         * 草稿箱文章保存延时队列
         */
        public static final String ARTICLE_DRAFT_DELAY_QUEUE = "article.draft.delay.queue";

        /**
         * 草稿箱文章死信路由键
         */
        public static final String ARTICLE_DRAFT_DEAD_ROUTING_KEY = "article.draft.delete.#";

        /**
         * 草稿箱死信队列
         */
        public static final String ARTICLE_DRAFT_DEAD_QUEUE = "article.draft.dead.queue";
    }

    /**
     * 搜索MQ常量
     */
    public static class SearchConstant {
        /**
         * 搜索交换器
         */
        public static final String SEARCH_EVENT_EXCHANGE = "search.event.exchange";

        /**
         * 添加文章消息队列
         */
        public static final String SEARCH_ADD_ARTICLE_QUEUE = "search.add.article.queue";

        /**
         * 添加文章路由键
         */
        public static final String SEARCH_ADD_ARTICLE_ROUTING_KEY = "search.add.article.#";

        /**
         * 删除文章消息队列
         */
        public static final String SEARCH_DELETE_ARTICLE_QUEUE = "search.delete.article.queue";

        /**
         * 删除文章路由键
         */
        public static final String SEARCH_DELETE_ARTICLE_ROUTING_KEY = "search.delete.article.#";
    }
}
