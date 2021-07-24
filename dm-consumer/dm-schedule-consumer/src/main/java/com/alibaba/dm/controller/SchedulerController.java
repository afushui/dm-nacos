package com.alibaba.dm.controller;


import com.alibaba.dm.service.SchedulerSeatService;
import com.alibaba.dm.service.SchedulerService;
import com.alibaba.dm.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/desc")
public class SchedulerController {
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private SchedulerSeatService schedulerSeatService;
    @PostMapping("/getTimePlan")
    public CommonResponse<List<SchedulerByItemIdResponse>> getTimePlan(@RequestBody Map<String,Object> map){
            return schedulerService.getTimePlan(Long.parseLong(map.get("itemId").toString()));
    }

    @PostMapping("/getPrice")
    public CommonResponse<List<SchedulerPriceResponse>>getPrice(@RequestBody Map<String,Object> map){
        try {
          return   schedulerService.queryItemPrice(Long.parseLong(map.get("scheduleId").toString()));
        }catch (Exception e){
           return VoUtil.returnFailure("1001",e.getMessage());

        }
    }

    @PostMapping("getComments")
    public CommonResponse<List<ItemCommentResponse>>queryItemComment(@RequestBody Map<String,Object> map){

            return schedulerService.queryItemComment(Long.parseLong(map.get("id").toString()));
    }
    @GetMapping("getComments")
    CommonResponse  commitItemComment(@RequestBody Map<String,Object> map){
        return schedulerService.commitItemComment(map);
    }

    //推荐
    @PostMapping("getRecommend")
    public CommonResponse<List<RecommendResponse>>queryItemRecommend(@RequestBody Map<String,Object> map){
        return schedulerService.getRecommend(Long.parseLong(map.get("itemTypeId").toString()),6);
    }
    @PostMapping("/seat/getSchedule")
    public CommonResponse<ScheduleIdResponse>queryCinemaSeatArrayByScheduleId(@RequestBody Map<String,Object> map){
        return schedulerSeatService.queryCinemaSeatArrayByScheduleId(Long.parseLong(map.get("scheduleId").toString()),Long.parseLong(map.get("cinemaId").toString()));
    }

    @PostMapping("/seat/getSeatList")
    public CommonResponse<ScheduleIdSeatResponse>queryOriginalCinemaSeatArray(@RequestBody Map<String,Object> map){
        return schedulerSeatService.queryOriginalCinemaSeatArray(Long.parseLong(map.get("scheduleId").toString()),Long.parseLong(map.get("cinemaId").toString()));
    }


}
