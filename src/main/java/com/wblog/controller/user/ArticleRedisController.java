package com.wblog.controller.user;

import com.wblog.common.utils.R;
import com.wblog.service.ArticleRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/article")
public class ArticleRedisController {
    @Autowired
    ArticleRedisService articleRedisService;

    @PostMapping("/collect")
    @ResponseBody
    public R collectOrCancel(@RequestParam("articleId") Long articleId,
                             @RequestParam("flag") Boolean flag) {
        articleRedisService.collectOrCancel(articleId, flag);
        return R.ok();
    }

    @PostMapping("/thumb")
    @ResponseBody
    public R thumbUpOrCancel(@RequestParam("articleId") Long articleId,
                             @RequestParam("flag") Boolean flag) {
        articleRedisService.thumbUpOrCancel(articleId, flag);
        return R.ok();
    }
}
