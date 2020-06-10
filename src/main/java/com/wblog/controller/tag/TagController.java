package com.wblog.controller.tag;

import java.util.List;

import com.wblog.annotation.SysLog;
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
    public String list(Model model){
        List<TagEntity> tags = tagService.list();
        model.addAttribute("tags", tags);
        return "admin/tag/list";
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		TagEntity tag = tagService.getById(id);

        return R.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TagEntity tag){
		tagService.save(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody TagEntity tag){
		tagService.updateById(tag);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog(business = "删除标签")
    @PostMapping("/{id}")
    @ResponseBody
    public R delete(@PathVariable("id") Long id){
		tagService.deleteByIds(id);
        return R.ok();
    }

}
