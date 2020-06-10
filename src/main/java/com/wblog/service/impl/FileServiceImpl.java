package com.wblog.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.wblog.common.utils.R;
import com.wblog.exception.PolicyException;
import com.wblog.properties.OssProperties;
import com.wblog.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    OSS ossClient;

    @Autowired
    OssProperties ossProperties;

    @Override
    public Map<String, String> imgPolicy() {

        String host = "https://" + ossProperties.getBucket() + "." + ossProperties.getEndpoint();
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        //String callbackUrl = "http://88.88.88.88:8888";
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/";

        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<>();
            respMap.put("accessid", ossProperties.getAccessKeyId());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            log.info("获取文件上传policy");
            return respMap;
        } catch (Exception e) {
            log.info("获取文件上传policy失败");
            throw new PolicyException("获取代理失败");
        }
    }
}
