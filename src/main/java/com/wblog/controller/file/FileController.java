package com.wblog.controller.file;

import com.wblog.common.utils.R;
import com.wblog.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/admin/file")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping
    @ResponseBody
    public R imgPolicy() {
        Map<String, String> respMap =  fileService.imgPolicy();
        return R.ok().put("data", respMap);
    }

}
