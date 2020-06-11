package com.wblog.controller.user;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.UserLogEntity;
import com.wblog.service.UserLogService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/userlog")
public class UserLogController {
    @Autowired
    private UserLogService userLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		UserLogEntity userLog = userLogService.getById(id);

        return R.ok().put("userLog", userLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody UserLogEntity userLog){
		userLogService.save(userLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody UserLogEntity userLog){
		userLogService.updateById(userLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("wblog:userlog:delete")
    public R delete(@RequestBody Long[] ids){
		userLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
