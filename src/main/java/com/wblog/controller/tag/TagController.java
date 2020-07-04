package com.wblog.controller.tag;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wblog.annotation.SysLog;
import com.wblog.common.utils.PageResult;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.TagEntity;
import com.wblog.service.TagService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@Controller
@RequestMapping("admin/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public String list(@RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(value = "size", required = false, defaultValue = "10") Long size,
                       Model model){
        PageHelper.startPage(page.intValue(), size.intValue());
        List<TagEntity> tags = tagService.list();
        PageInfo<TagEntity> pageInfo = new PageInfo<>(tags);
        PageResult pageResult = new PageResult(pageInfo);
        model.addAttribute("page", pageResult);
        return "admin/tag/list";
    }
}
