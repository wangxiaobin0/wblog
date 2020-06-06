package com.wblog.controller.column;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.ColumnEntity;
import com.wblog.service.ColumnService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/column")
public class ColumnController {
    @Autowired
    private ColumnService columnService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("wblog:column:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = columnService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("wblog:column:info")
    public R info(@PathVariable("id") Long id){
		ColumnEntity column = columnService.getById(id);

        return R.ok().put("column", column);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("wblog:column:save")
    public R save(@RequestBody ColumnEntity column){
		columnService.save(column);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("wblog:column:update")
    public R update(@RequestBody ColumnEntity column){
		columnService.updateById(column);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("wblog:column:delete")
    public R delete(@RequestBody Long[] ids){
		columnService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
