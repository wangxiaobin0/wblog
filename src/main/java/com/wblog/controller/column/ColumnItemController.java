package com.wblog.controller.column;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.wblog.annotation.SysLog;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import com.wblog.model.entity.ArticleEntity;
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
     * 查询已添加列表和未添加列表
     */
    @GetMapping("/list")
    public String list(@RequestParam("columnId") Long columnId, Model model){
        return "/admin/column/columnModal :: columnItem";
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ColumnItemEntity columnItem = columnItemService.getById(id);

        return R.ok().put("columnItem", columnItem);
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

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ColumnItemEntity columnItem){
		columnItemService.updateById(columnItem);

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
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		columnItemService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
