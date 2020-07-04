package com.wblog.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * 分页结果类
 * @param <T>
 */
public class PageResult<T> {

    @Getter
    @Setter
    private Long page;

    @Getter
    @Setter
    private Long size;

    @Getter
    @Setter
    private Long totalPage;

    @Getter
    @Setter
    private Long totalNum;

    @Getter
    @Setter
    private List list;

    public PageResult() {}

    public PageResult(IPage iPage) {
        this.page = iPage.getCurrent();
        this.size = iPage.getSize();
        this.totalPage = iPage.getPages();
        this.totalNum = iPage.getTotal();
        this.list = iPage.getRecords();
    }

    public PageResult(IPage iPage, List records) {
        this.page = iPage.getCurrent();
        this.size = iPage.getSize();
        this.totalPage = iPage.getPages();
        this.totalNum = iPage.getTotal();
        this.list = records;
    }

    public PageResult(PageInfo pageInfo) {
        this.page = (long) pageInfo.getPageNum();
        this.size = (long) pageInfo.getPageSize();
        this.totalPage = (long) pageInfo.getPages();
        this.totalNum = pageInfo.getTotal();
        this.list = pageInfo.getList();
    }
}
