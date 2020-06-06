package com.wblog.controller.tag;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.TagEntity;
import com.wblog.service.TagService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("wblog:tag:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tagService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("wblog:tag:info")
    public R info(@PathVariable("id") Long id){
		TagEntity tag = tagService.getById(id);

        return R.ok().put("tag", tag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("wblog:tag:save")
    public R save(@RequestBody TagEntity tag){
		tagService.save(tag);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("wblog:tag:update")
    public R update(@RequestBody TagEntity tag){
		tagService.updateById(tag);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("wblog:tag:delete")
    public R delete(@RequestBody Long[] ids){
		tagService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
