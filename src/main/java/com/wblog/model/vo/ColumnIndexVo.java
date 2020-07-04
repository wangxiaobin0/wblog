package com.wblog.model.vo;

import com.wblog.common.utils.PageResult;
import lombok.Data;

import java.util.List;

@Data
public class ColumnIndexVo {
    PageResult pageResult;
    List<ColumnVo> bannerList;
}
