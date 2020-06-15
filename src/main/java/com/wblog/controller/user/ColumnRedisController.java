package com.wblog.controller.user;

import com.wblog.annotation.SysLog;
import com.wblog.common.utils.R;
import com.wblog.service.ColumnRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/column")
public class ColumnRedisController {

    @Autowired
    ColumnRedisService columnRedisService;

    /**
     * 添加/取消订阅
     * @param columnId
     * @param flag
     * @return
     */
    @SysLog(business = "添加或取消订阅")
    @ResponseBody
    @PostMapping("/subscribe")
    public R subscribe(@RequestParam("columnId") Long columnId,
                 @RequestParam("flag") Boolean flag) {
        columnRedisService.subscribe(columnId, flag);
        return R.ok();
    }
}
