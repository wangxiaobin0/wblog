package com.wblog.controller;

import java.util.Arrays;
import java.util.Map;

import com.wblog.model.entity.MqFailMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.wblog.service.MqFailMessageService;
import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.R;


/**
 * @author wangxb
 * @email
 */
@Controller
@RequestMapping("mq/message/fail")
public class MqFailMessageController {
    @Autowired
    private MqFailMessageService mqFailMessageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = mqFailMessageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MqFailMessageEntity mqFailMessage = mqFailMessageService.getById(id);

        return R.ok().put("mqFailMessage", mqFailMessage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MqFailMessageEntity mqFailMessage) {
        mqFailMessageService.save(mqFailMessage);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody MqFailMessageEntity mqFailMessage) {
        mqFailMessageService.updateById(mqFailMessage);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        mqFailMessageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
