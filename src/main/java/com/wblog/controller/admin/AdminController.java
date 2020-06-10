package com.wblog.controller.admin;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.AdminEntity;
import com.wblog.service.AdminService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = adminService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		AdminEntity admin = adminService.getById(id);

        return R.ok().put("admin", admin);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("wblog:admin:save")
    public R save(@RequestBody AdminEntity admin){
		adminService.save(admin);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("wblog:admin:update")
    public R update(@RequestBody AdminEntity admin){
		adminService.updateById(admin);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("wblog:admin:delete")
    public R delete(@RequestBody Long[] ids){
		adminService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
