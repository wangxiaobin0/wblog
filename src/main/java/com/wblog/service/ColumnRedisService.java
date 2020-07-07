package com.wblog.service;

import com.wblog.model.vo.ColumnIndexVo;

import java.util.List;
import java.util.Set;

public interface ColumnRedisService {

    Long getCount(Long columnId);

    void subscribe(Long columnId, Boolean flag);

    Boolean hasSubscribe(Long columnId, String userKey);

    Set<String> getUserSubscribeList();
}
