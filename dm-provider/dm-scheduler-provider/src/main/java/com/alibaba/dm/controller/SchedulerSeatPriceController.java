package com.alibaba.dm.controller;

import com.alibaba.dm.dao.SchedulerSeatPriceDao;
import com.alibaba.dm.entity.scheduler.SchedulerSeatPrice;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SchedulerSeatPriceController {
    @Resource
    private SchedulerSeatPriceDao schedulerSeatPriceDao;

    //根据商品排期查询商品价格 http://192.168.9.151:7600/item/api/p/queryItemPrice
    @PostMapping("queryItemPrice")
    public List<SchedulerSeatPrice> queryItemPrice(@RequestParam("scheduleId") Long scheduleId){
        QueryWrapper<SchedulerSeatPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("scheduleId",scheduleId);
        return schedulerSeatPriceDao.selectList(queryWrapper);
    }

    //
    @PostMapping("getSchedulerSeatPriceBySchedulerIdAndArea")
    public SchedulerSeatPrice getSchedulerSeatPriceBySchedulerIdAndArea(@RequestParam("scheduleId") Long scheduleId,@RequestParam("areaLevel") Long areaLevel){
        QueryWrapper<SchedulerSeatPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("areaLevel",areaLevel);
        queryWrapper.eq("scheduleId",scheduleId);
        return schedulerSeatPriceDao.selectOne(queryWrapper);
    }
}
