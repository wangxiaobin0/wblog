package com.wblog.service.impl;

import com.wblog.common.utils.PageUtils;
import com.wblog.common.utils.Query;
import com.wblog.model.vo.SystemLogVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.wblog.dao.SystemLogDao;
import com.wblog.model.entity.SystemLogEntity;
import com.wblog.service.SystemLogService;


@Service("userLogService")
public class SystemLogServiceImpl extends ServiceImpl<SystemLogDao, SystemLogEntity> implements SystemLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SystemLogEntity> page = this.page(
                new Query<SystemLogEntity>().getPage(params),
                new QueryWrapper<SystemLogEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SystemLogVo> getLog() {
        List<SystemLogEntity> list = this.list(new QueryWrapper<SystemLogEntity>().comment("limit 10").orderByDesc("time"));
        return list.stream().map(log -> {
            SystemLogVo logVo = new SystemLogVo();
            BeanUtils.copyProperties(log, logVo);
            return logVo;
        }).collect(Collectors.toList());
    }

}