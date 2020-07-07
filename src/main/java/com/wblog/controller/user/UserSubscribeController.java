package com.wblog.controller.user;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.UserSubscribeEntity;
import com.wblog.service.UserSubscribeService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/usersubscribe")
public class UserSubscribeController {
    @Autowired
    private UserSubscribeService userSubscribeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userSubscribeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		UserSubscribeEntity userSubscribe = userSubscribeService.getById(id);

        return R.ok().put("userSubscribe", userSubscribe);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody UserSubscribeEntity userSubscribe){
		userSubscribeService.save(userSubscribe);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody UserSubscribeEntity userSubscribe){
		userSubscribeService.updateById(userSubscribe);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		userSubscribeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
