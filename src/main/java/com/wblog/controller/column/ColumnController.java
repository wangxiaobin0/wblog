package com.wblog.controller.column;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.wblog.annotation.SysLog;
import com.wblog.common.utils.PageResult;
import com.wblog.common.utils.R;
import com.wblog.model.vo.ColumnDetailVo;
import com.wblog.model.vo.ColumnVo;
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
     * 管理端：跳转添加页面
     *
     * @return
     */
    @GetMapping
    public String goToAddPage() {
        return "/admin/column/add";
    }

    /**
     * 管理端：列表
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(value = "size", required = false, defaultValue = "5") Long size,
                       Model model) {
        PageResult pageResult = columnService.listByPage(page, size);
        model.addAttribute("page", pageResult);
        return "/admin/column/list";
    }

    /**
     * 管理端：专栏详情
     */
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
     * 添加或取消轮播
     * @param columnId 专栏id
     * @param flag 是否添加为轮播
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

    /**
     * 查询文章未添加到的专栏
     * @param articleId
     * @param model
     * @return
     */
    @GetMapping("/unAdd")
    public String unAddColumn(@RequestParam("articleId") Long articleId, Model model) {
        List<ColumnVo> unAddColumn = columnService.unAddColumn(articleId);
        model.addAttribute("unAddColumn", unAddColumn);
        model.addAttribute("articleId", articleId);
        return "/admin/fragment/column :: column";
    }
}
