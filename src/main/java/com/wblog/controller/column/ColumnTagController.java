package com.wblog.controller.column;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.ColumnTagEntity;
import com.wblog.service.ColumnTagService;



/**
 * 专栏/标签关联表
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/columntag")
public class ColumnTagController {
    @Autowired
    private ColumnTagService columnTagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = columnTagService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ColumnTagEntity columnTag = columnTagService.getById(id);

        return R.ok().put("columnTag", columnTag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ColumnTagEntity columnTag){
		columnTagService.save(columnTag);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody ColumnTagEntity columnTag){
		columnTagService.updateById(columnTag);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		columnTagService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
