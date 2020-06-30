package com.wblog.service;

import com.wblog.model.vo.SearchParamVo;
import com.wblog.model.vo.SearchResultVo;

import java.io.IOException;

public interface SearchService {
    Boolean add(Long articleId) throws IOException;

    SearchResultVo search(SearchParamVo key) throws IOException;

    Boolean delete(Long id) throws IOException;
}
