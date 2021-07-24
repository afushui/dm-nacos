package com.alibaba.dm.client.schedule;

import com.alibaba.dm.entity.scheduler.Scheduler;
import com.alibaba.dm.entity.scheduler.SchedulerSeat;
import com.alibaba.dm.entity.scheduler.SchedulerSeatPrice;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("dm-scheduler-provider")
public interface SchedulerClient {

    @PostMapping("findById")
    public Scheduler findById(@RequestParam("id")Long id);


    @PostMapping("findByItemId")
    public List<Scheduler> findByItemId(@RequestParam("itemId") Long itemId);



    //根据排期获取剧场座位信息
    @PostMapping("byScheduleId")
    public List<SchedulerSeat> findByScheduleId(@RequestParam("scheduleId")Long scheduleId);


    //根据x,y,id 判断座位有没有被购买
    @PostMapping("getSchedulerSeatByOrder")
    public SchedulerSeat getSchedulerSeatByOrder(@RequestBody()SchedulerSeat schedulerSeat );

    //修改 排期状态
    @PostMapping("updateSchedulerSeat")
    public int updateSchedulerSeat(@RequestBody() SchedulerSeat schedulerSeat);

    @PostMapping("modifySchedulerSeat")
    void modifySchedulerSeat(@RequestBody() SchedulerSeat seat);

    //根据商品排期查询商品价格 http://192.168.9.151:7600/item/api/p/queryItemPrice
    @PostMapping("queryItemPrice")
    public List<SchedulerSeatPrice> queryItemPrice(@RequestParam("scheduleId") Long scheduleId);

    @PostMapping("getSchedulerSeatPriceBySchedulerIdAndArea")
    public SchedulerSeatPrice getSchedulerSeatPriceBySchedulerIdAndArea(@RequestParam("scheduleId") Long scheduleId,@RequestParam("areaLevel") Long areaLevel);
}
