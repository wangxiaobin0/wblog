package com.wblog.controller.admin;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.AdminProfileEntity;
import com.wblog.service.AdminProfileService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/adminprofile")
public class AdminProfileController {
    @Autowired
    private AdminProfileService adminProfileService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = adminProfileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("wblog:adminprofile:info")
    public R info(@PathVariable("id") Long id){
		AdminProfileEntity adminProfile = adminProfileService.getById(id);

        return R.ok().put("adminProfile", adminProfile);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("wblog:adminprofile:save")
    public R save(@RequestBody AdminProfileEntity adminProfile){
		adminProfileService.save(adminProfile);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("wblog:adminprofile:update")
    public R update(@RequestBody AdminProfileEntity adminProfile){
		adminProfileService.updateById(adminProfile);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("wblog:adminprofile:delete")
    public R delete(@RequestBody Long[] ids){
		adminProfileService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
