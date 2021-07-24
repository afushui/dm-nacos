package com.alibaba.dm.service;


import com.alibaba.dm.client.schedule.SchedulerClient;
import com.alibaba.dm.entity.scheduler.Scheduler;
import com.alibaba.dm.entity.scheduler.SchedulerSeat;
import com.alibaba.dm.entity.scheduler.SchedulerSeatPrice;
import com.alibaba.dm.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchedulerSeatService {
    @Autowired
    SchedulerClient schedulerClient;


    public CommonResponse<ScheduleIdSeatResponse>queryOriginalCinemaSeatArray(Long scheduleId, Long cinemaId){

        List<SchedulerSeat> dmCinemaSeatList = schedulerClient.findByScheduleId(scheduleId);
        //拼接剧场座位的格式
        Map<Integer, String> map = new HashMap<>();
        for(SchedulerSeat seat : dmCinemaSeatList){
            String value = "";
            if(seat.getAreaLevel() == 0){
                value = "_";
            }else if (seat.getAreaLevel() == 1) {
                value = "a";
            } else if (seat.getAreaLevel() == 2) {
                value = "b";
            } else if (seat.getAreaLevel() == 3) {
                value = "c";
            } else {
                value = "_";
            }
            if(map.get(seat.getX().intValue()) != null) {
                map.put(seat.getX().intValue(),map.get(seat.getX().intValue()) + value);
            } else {
                map.put(seat.getX().intValue(),value);
            }
        }
        List<String> list = new ArrayList<>();

        map.forEach((k,v)->list.add(v));
        ScheduleIdSeatResponse scheduleIdSeatResponse = new ScheduleIdSeatResponse();
        scheduleIdSeatResponse.setSeatArray(list);
        scheduleIdSeatResponse.setCinemaId(cinemaId);
        return VoUtil.returnSuccess(null,scheduleIdSeatResponse);

    }

    public CommonResponse<ScheduleIdResponse>queryCinemaSeatArrayByScheduleId(Long scheduleId, Long cinemaId){
        Scheduler dmScheduler = schedulerClient.findById(scheduleId);
        //实例化 排期视图
        ScheduleIdResponse scheduleIdVo=new ScheduleIdResponse();
        scheduleIdVo.setCinemaId(dmScheduler.getCinemaId());
        scheduleIdVo.setScheduleId(scheduleId);
        //价格视图
        //座位视图
        List<SeatPriceListResponse> seatPriceListVos=new ArrayList<>();
        //首先循环座位状态信息   根据排期id获得座位的价格
        List<SchedulerSeatPrice> dmSchedulerSeatPriceList = schedulerClient.queryItemPrice(scheduleId);
        for(SchedulerSeatPrice dmschedulerSeatPrice : dmSchedulerSeatPriceList){
            SeatPriceListResponse seatPriceListVo = new SeatPriceListResponse();
            String state="";
            if(dmschedulerSeatPrice.getAreaLevel()==1){
                state="A";
            }else if(dmschedulerSeatPrice.getAreaLevel()==2){
                state="B";
            }else if(dmschedulerSeatPrice.getAreaLevel()==3){
                state="C";
            }
            seatPriceListVo.setAreaLevelName(state);
            seatPriceListVo.setPrice(dmschedulerSeatPrice.getPrice());
            seatPriceListVos.add(seatPriceListVo);
        }
        //循环座位
        List<SchedulerSeat> dmSchedulerSeatList = schedulerClient.findByScheduleId(scheduleId);
        List<SeatInfoListResponse> listVos=new ArrayList<>();
        for(SchedulerSeat dmSchedulerSeat : dmSchedulerSeatList){
            //座位视图
            SeatInfoListResponse seatInfoListVo = new SeatInfoListResponse();
            BeanUtils.copyProperties(dmSchedulerSeat,seatInfoListVo);
            seatInfoListVo.setCinemaId(cinemaId);
            if(dmSchedulerSeat.getAreaLevel() == 1){
                seatInfoListVo.setAreaLevel("A");
            }else if(dmSchedulerSeat.getAreaLevel() == 2){
                seatInfoListVo.setAreaLevel("B");
            }else{
                seatInfoListVo.setAreaLevel("C");
            }
            listVos.add(seatInfoListVo);
        }
        scheduleIdVo.setSeatInfoList(listVos);
        scheduleIdVo.setSeatPriceList(seatPriceListVos);
        return  VoUtil.returnSuccess(null,scheduleIdVo);
    }



}
