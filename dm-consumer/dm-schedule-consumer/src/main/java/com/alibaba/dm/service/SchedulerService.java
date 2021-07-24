package com.alibaba.dm.service;

import com.alibaba.dm.client.base.ImageClient;
import com.alibaba.dm.client.item.ItemClient;
import com.alibaba.dm.client.schedule.SchedulerClient;
import com.alibaba.dm.entity.base.Image;
import com.alibaba.dm.entity.item.Cinema;
import com.alibaba.dm.entity.item.Item;
import com.alibaba.dm.entity.item.ItemComment;
import com.alibaba.dm.entity.scheduler.Scheduler;
import com.alibaba.dm.entity.scheduler.SchedulerSeatPrice;
import com.alibaba.dm.vo.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SchedulerService {
    @Autowired
    ItemClient itemClient;
    @Autowired
    SchedulerClient schedulerClient;
    @Autowired
    ImageClient imageClient;


    //根据商品排期查询商品价格
    public CommonResponse<List<SchedulerByItemIdResponse>> getTimePlan(Long itemId) {
        List<Scheduler> schedulers= schedulerClient.findByItemId(itemId);
        List<SchedulerByItemIdResponse> schedulerByItemIdVos=new ArrayList<>();
        for(Scheduler scheduler:schedulers){
            SchedulerByItemIdResponse schedulerByItemIdVo=new SchedulerByItemIdResponse(scheduler.getId(),
                    scheduler.getTitle(),scheduler.getItemId(),scheduler.getStartTime().toString(),
                    scheduler.getEndTime().toString(),scheduler.getCinemaId());
            schedulerByItemIdVos.add(schedulerByItemIdVo);

        }
        return VoUtil.returnSuccess("0000",schedulerByItemIdVos);
    }

    //根据商品排期查询商品价格
    public CommonResponse<List<SchedulerPriceResponse>> queryItemPrice(Long scheduleId){
        List<SchedulerSeatPrice> schedulerSeatPrices = schedulerClient.queryItemPrice(scheduleId);
        List<SchedulerPriceResponse>priceResponseList = new ArrayList<>();
        schedulerSeatPrices.forEach(schedulerSeatPrice -> {
            SchedulerPriceResponse priceResponse=new SchedulerPriceResponse();
            priceResponse.setId(schedulerSeatPrice.getId());
            priceResponse.setScheduleId(schedulerSeatPrice.getScheduleId());
            priceResponse.setIsHaveSeat(schedulerSeatPrice.getSeatNum()>0?1:schedulerSeatPrice.getSeatNum().longValue());
            priceResponse.setPrice(schedulerSeatPrice.getPrice());

            priceResponseList.add(priceResponse);
        });
        return VoUtil.returnSuccess("0000",priceResponseList);
    }

    //根据商品id查询剧评
    public CommonResponse<List<ItemCommentResponse>> queryItemComment(Long id){
        List<ItemComment> itemComments = itemClient.commentByItemId(id);
        List<ItemCommentResponse> itemCommentResponseList = new ArrayList<>();
        itemComments.forEach(dmItemComment -> {
            ItemCommentResponse itemCommentResponse = new ItemCommentResponse();
            BeanUtils.copyProperties(dmItemComment,itemCommentResponse);
//            DmImage dmImage = imageClient.ByUserImage(dmItemComment.getUserId());
//            if (dmImage!=null){
//                itemCommentResponse.setImgUrl(dmImage.getImgUrl());
//            }
            itemCommentResponseList.add(itemCommentResponse);
        });
        return VoUtil.returnSuccess("0000",itemCommentResponseList);
    }

    //商品详情页API 添加剧评
    public CommonResponse commitItemComment(Map<String, Object> map){
        itemClient.addItemComment(map);
        return VoUtil.returnSuccess(null,null);
    }
    //商品详情页API 推荐
    public CommonResponse<List<RecommendResponse>>getRecommend(Long itemTypeId, Integer limit){
        List<RecommendResponse> recommendResponseList = new ArrayList<>();
        List<Item> itemList = null;
        if (itemTypeId>9){
            itemList=  itemClient.findByItemType2Id(itemTypeId, limit);
        }else {
            itemList= itemClient.findByItemType1Id(itemTypeId, limit);
        }

        itemList.forEach(dmItem -> {
            RecommendResponse recommendResponse =new RecommendResponse();
            BeanUtils.copyProperties(dmItem,recommendResponse);
            Cinema cinema = itemClient.findCinemaById(dmItem.getCinemaId());
            recommendResponse.setAddress(cinema.getAddress());
            recommendResponse.setAreaId(cinema.getAreaId());
            recommendResponse.setAreaName(cinema.getAreaName());
            recommendResponse.setStartDate(dmItem.getStartTime().toString());
            recommendResponse.setEndDate(dmItem.getEndTime().toString());
            recommendResponse.setMinPrice(dmItem.getMinPrice());
            Image itemImage = imageClient.getItemImage(dmItem.getId());
            if (itemImage!=null) {
                recommendResponse.setImgUrl(itemImage.getImgUrl());
            }
            recommendResponseList.add(recommendResponse);
        });
        return VoUtil.returnSuccess("0000",recommendResponseList);

    }


}
