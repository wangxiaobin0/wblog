package com.wblog.service;

public interface ColumnRedisService {

    Long getCount(Long columnId);

    void subscribe(Long columnId, Boolean flag);

    Boolean hasSubscribe(Long columnId, String userKey);
}
