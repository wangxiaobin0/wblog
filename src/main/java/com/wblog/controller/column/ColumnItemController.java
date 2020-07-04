package com.wblog.controller.column;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wblog.annotation.SysLog;
import com.wblog.common.utils.PageResult;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import com.wblog.model.entity.ArticleEntity;
import com.wblog.model.vo.ColumnItemVo;
import com.wblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.ColumnItemEntity;
import com.wblog.service.ColumnItemService;

import javax.validation.Valid;


/**
 * 
 *
 * @author wangxb
 * @email 
 */
@Controller
@RequestMapping("/admin/column/item")
public class ColumnItemController {
    @Autowired
    private ColumnItemService columnItemService;

    /**
     * 查询未添加列表
     */
    @GetMapping("/list")
    public String list(@RequestParam("columnId") Long columnId, Model model){
        List<ColumnItemVo> unAddList = columnItemService.getUnAddArticle(columnId);
        model.addAttribute("unAddList", unAddList);
        model.addAttribute("columnId", columnId);
        return "/admin/fragment/columnItem :: columnItem";
    }
    /**
     * 保存
     */
    @SysLog(business = "专栏添加文章")
    @PostMapping
    @ResponseBody
    public R save(@Valid ColumnItemEntity columnItem){
		columnItemService.addToColumn(columnItem);
        return R.ok();
    }

    @SysLog(business = "修改专栏中文章的排序")
    @ResponseBody
    @PostMapping("/sort")
    public R changeSort(@RequestParam("id") Long id, @RequestParam("sort") Boolean sort) {
        columnItemService.changeSort(id, sort);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog(business = "从专栏中删除")
    @ResponseBody
    @PostMapping("/{id}")
    public R delete(@PathVariable("id") Long id){
        columnItemService.removeById(id);
        return R.ok();
    }

    @GetMapping
    public String queryColumnItemByPage(@RequestParam("columnId") Long id,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                                        @RequestParam(value = "size", required = false, defaultValue = "5") Long size,
                                        Model model) {
        PageResult pageResult = columnItemService.queryColumnItemByPage(id, page, size);
        model.addAttribute("page", pageResult);
        return "/admin/column/articleFragment :: articleFragment";
    }

}
