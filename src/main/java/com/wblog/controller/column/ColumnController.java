package com.wblog.controller.column;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @author wangxb
 * @email
 */
@Controller
@RequestMapping("/admin/column")
public class ColumnController {
    @Autowired
    private ColumnService columnService;

    /**
     * 管理端：列表
     */
    @GetMapping("/list")
    public String list(@RequestParam Map<String, Object> params, Model model) {
        List<ColumnEntity> list = columnService.list();
        model.addAttribute("list", list);
        return "/admin/column/list";
    }

    /**
     * 管理端：跳转添加页面
     *
     * @return
     */
    @GetMapping
    public String goToAddPage() {
        return "/admin/column/add";
    }

    /**
     * 管理端：专栏详情
     */
    @SysLog(business = "专栏详情")
    @GetMapping("/{id}")
    public String columnDetail(@PathVariable("id") Long id, Model model) throws ExecutionException, InterruptedException {
        ColumnDetailVo columnVo = columnService.columnDetail(id);
        model.addAttribute("detail", columnVo);
        return "admin/column/detail";
    }

    /**
     * 管理端：保存
     */
    @SysLog(business = "新增专栏")
    @ResponseBody
    @PostMapping
    public R save(@Valid ColumnEntity column) {
        columnService.add(column);
        return R.ok();
    }

    /**
     * 修改
     */
    @Deprecated
    @PostMapping("/update")
    public R update(@RequestBody ColumnEntity column) {
        columnService.updateById(column);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        columnService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 添加或取消轮播
     * @param columnId
     * @param flag
     * @return
     */
    @SysLog(business = "添加或取消轮播")
    @ResponseBody
    @PostMapping("/banner")
    public R addOrCancelBanner(@RequestParam("columnId") Long columnId,
                       @RequestParam("banner") Boolean flag) {
        columnService.addOrCancelBanner(columnId, flag);
        return R.ok();
    }
}
