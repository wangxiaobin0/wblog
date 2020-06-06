package com.wblog.controller.column;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.ColumnItemEntity;
import com.wblog.service.ColumnItemService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/columnitem")
public class ColumnItemController {
    @Autowired
    private ColumnItemService columnItemService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("wblog:columnitem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = columnItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("wblog:columnitem:info")
    public R info(@PathVariable("id") Long id){
		ColumnItemEntity columnItem = columnItemService.getById(id);

        return R.ok().put("columnItem", columnItem);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("wblog:columnitem:save")
    public R save(@RequestBody ColumnItemEntity columnItem){
		columnItemService.save(columnItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("wblog:columnitem:update")
    public R update(@RequestBody ColumnItemEntity columnItem){
		columnItemService.updateById(columnItem);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("wblog:columnitem:delete")
    public R delete(@RequestBody Long[] ids){
		columnItemService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
