package com.wblog.controller.column;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.wblog.annotation.SysLog;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import com.wblog.interceptor.AdminRequestInterceptor;
import com.wblog.model.entity.ArticleEntity;
import com.wblog.model.vo.ColumnDetailVo;
import com.wblog.service.ArticleService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.ColumnEntity;
import com.wblog.service.ColumnService;

import javax.validation.Valid;


/**
 * 
 *
 * @author wangxb
 * @email 
 */
@Controller
@RequestMapping("/admin/column")
public class ColumnController {
    @Autowired
    private ColumnService columnService;

    @Autowired
    private ArticleService articleService;
    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(@RequestParam Map<String, Object> params, Model model){
        List<ColumnEntity> list = columnService.list();
        model.addAttribute("list", list);
        return "/admin/column/list";
    }

    @GetMapping
    public String goToAddPage() {
        return "/admin/column/add";
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public String columnDetail(@PathVariable("id") Long id, Model model){
        try {
            ColumnDetailVo columnVo = columnService.columnDetail(id);
            model.addAttribute("detail", columnVo);
        } catch (Exception e) {
            model.addAttribute("detail", null);
            e.printStackTrace();
        }
        return "admin/column/detail";
    }

    /**
     * 保存
     */
    @SysLog(business = "新增专栏")
    @ResponseBody
    @PostMapping
    public R save(@Valid ColumnEntity column){
		columnService.add(column);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ColumnEntity column){
		columnService.updateById(column);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		columnService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
