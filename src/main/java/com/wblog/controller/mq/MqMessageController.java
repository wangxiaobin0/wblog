package com.wblog.controller.mq;

import java.util.Arrays;
import java.util.Map;

import com.wblog.model.entity.MqMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wblog.service.MqMessageService;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;



/**
 * 
 *
 * @author wangxb
 * @email 
 */
@RestController
@RequestMapping("wblog/mqmessage")
public class MqMessageController {
    @Autowired
    private MqMessageService mqMessageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("wblog:mqmessage:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mqMessageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MqMessageEntity mqMessage = mqMessageService.getById(id);

        return R.ok().put("mqMessage", mqMessage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MqMessageEntity mqMessage){
		mqMessageService.save(mqMessage);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody MqMessageEntity mqMessage){
		mqMessageService.updateById(mqMessage);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    //@RequiresPermissions("wblog:mqmessage:delete")
    public R delete(@RequestBody Long[] ids){
		mqMessageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
