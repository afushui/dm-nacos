package com.alibaba.dm.controller;

import com.alibaba.dm.dao.SchedulerDao;
import com.alibaba.dm.entity.scheduler.Scheduler;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SchedulerController {
    @Resource
    private SchedulerDao schedulerDao;

    @PostMapping("findById")
    public Scheduler findById(@RequestParam("id")Long id){
        return schedulerDao.selectById(id);
    }

    //根据商品ID查询商品排期 http://192.168.9.151:7600/item/api/p/queryItemScheduler
    @PostMapping("findByItemId")
    public List<Scheduler> findByItemId(@RequestParam("itemId") Long itemId){
        QueryWrapper<Scheduler>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemId",itemId);
        return schedulerDao.selectList(queryWrapper);
    }
}
