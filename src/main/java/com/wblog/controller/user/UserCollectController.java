package com.wblog.controller.user;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.UserCollectEntity;
import com.wblog.service.UserCollectService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/usercollect")
public class UserCollectController {
    @Autowired
    private UserCollectService userCollectService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userCollectService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		UserCollectEntity userCollect = userCollectService.getById(id);

        return R.ok().put("userCollect", userCollect);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody UserCollectEntity userCollect){
		userCollectService.save(userCollect);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody UserCollectEntity userCollect){
		userCollectService.updateById(userCollect);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		userCollectService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
