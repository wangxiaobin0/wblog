package com.wblog.controller.user;

import java.util.Arrays;
import java.util.Map;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.model.entity.UserMessageEntity;
import com.wblog.service.UserMessageService;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/usermessage")
public class UserMessageController {
    @Autowired
    private UserMessageService userMessageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("wblog:usermessage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userMessageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("wblog:usermessage:info")
    public R info(@PathVariable("id") Long id){
		UserMessageEntity userMessage = userMessageService.getById(id);

        return R.ok().put("userMessage", userMessage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("wblog:usermessage:save")
    public R save(@RequestBody UserMessageEntity userMessage){
		userMessageService.save(userMessage);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("wblog:usermessage:update")
    public R update(@RequestBody UserMessageEntity userMessage){
		userMessageService.updateById(userMessage);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("wblog:usermessage:delete")
    public R delete(@RequestBody Long[] ids){
		userMessageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
