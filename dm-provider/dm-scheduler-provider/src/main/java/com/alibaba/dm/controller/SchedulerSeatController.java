package com.alibaba.dm.controller;

import com.alibaba.dm.dao.SchedulerSeatDao;
import com.alibaba.dm.entity.scheduler.SchedulerSeat;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SchedulerSeatController {
    @Resource
    private SchedulerSeatDao schedulerSeatDao;

    //根据排期获取剧场座位信息
    @PostMapping("byScheduleId")
    public List<SchedulerSeat> findByScheduleId(@RequestParam("scheduleId")Long scheduleId){
        QueryWrapper<SchedulerSeat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("scheduleId",scheduleId);
        List<SchedulerSeat> schedulerSeats = schedulerSeatDao.selectList(queryWrapper);
        return schedulerSeats;
    }

    //根据x,y,id 判断座位有没有被购买
    @PostMapping("getSchedulerSeatByOrder")
    public SchedulerSeat getSchedulerSeatByOrder(@RequestBody()SchedulerSeat schedulerSeat ){
        QueryWrapper<SchedulerSeat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("x",schedulerSeat.getX());
        queryWrapper.eq("y",schedulerSeat.getY());
        queryWrapper.eq("scheduleId",schedulerSeat.getScheduleId());
        return schedulerSeatDao.selectOne(queryWrapper);
    }


    //修改 排期状态
    @PostMapping("updateSchedulerSeat")
    public int updateSchedulerSeat(@RequestBody() SchedulerSeat schedulerSeat){
        return schedulerSeatDao.updateById(schedulerSeat);
    }

    //modifySchedulerSeat

    @PostMapping("modifySchedulerSeat")
    void modifySchedulerSeat(@RequestBody() SchedulerSeat seat){
        QueryWrapper<SchedulerSeat> queryWrapper = new QueryWrapper<>();

    }
}
