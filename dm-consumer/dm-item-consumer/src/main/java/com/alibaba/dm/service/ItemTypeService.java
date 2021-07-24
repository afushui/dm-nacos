package com.alibaba.dm.service;

import com.alibaba.dm.client.base.ImageClient;
import com.alibaba.dm.client.item.ItemClient;
import com.alibaba.dm.entity.base.Image;
import com.alibaba.dm.entity.item.Cinema;
import com.alibaba.dm.entity.item.Item;
import com.alibaba.dm.entity.item.ItemType;
import com.alibaba.dm.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ItemTypeService {

    @Autowired
    ItemClient dmItemClient;
    @Autowired
    ImageClient dmImageClient;

    /**
     * 首页商品信息+相关热门
     * @return
     */
    public CommonResponse<List<ItemTypeResponse>> navList(){
        List<ItemType> dmItemTypeList = dmItemClient.findByParent(0L);
        List<ItemTypeResponse> itemTypeResponses=new ArrayList<>();
        dmItemTypeList.forEach(dmItemType -> {
            ItemTypeResponse itemTypeResponse=new ItemTypeResponse();
            BeanUtils.copyProperties(dmItemType,itemTypeResponse);
            //热门商品查询
            List<Item>dmItems = dmItemClient.itemIsBannerList(dmItemType.getId());
            List<ItemResponse>hotItems = new ArrayList<>();
            for (int i = 0; i <5 ; i++) {
                Item dmItem = dmItems.get(i);
                Image dmImage = dmImageClient.getItemImage(dmItem.getId());
                ItemResponse itemResponse = new ItemResponse();
                if (dmImage!=null) {
                    itemResponse.setImgUrl(dmImage.getImgUrl());
                }
                BeanUtils.copyProperties(dmItem,itemResponse);
                hotItems.add(itemResponse);
            }
            itemTypeResponse.setHotItems(hotItems);
            List<ItemType>childrenList=dmItemClient.findByParent(dmItemType.getId());
            List<ItemTypeResponse>children =new ArrayList<>();
            childrenList.forEach(child->{
                ItemTypeResponse itemTypeResponseChild=new ItemTypeResponse();
                BeanUtils.copyProperties(child,itemTypeResponseChild);
                children.add(itemTypeResponseChild);
            });
            itemTypeResponse.setChildren(children);
            itemTypeResponses.add(itemTypeResponse);
        });
        return VoUtil.returnSuccess("0000",itemTypeResponses);
    }

    /**
     * 轮播
     */
    public CommonResponse<List<ItemResponse>>carousel(){
        List<ItemResponse>itemResponses =new ArrayList<>();
        List<Item>list=dmItemClient.carouselIsBanner();
        list.forEach(dmItem -> {
            ItemResponse itemResponse = new ItemResponse();
            BeanUtils.copyProperties(dmItem,itemResponse);
            List<Image> dmImages = dmImageClient.itemCarouselList(dmItem.getId());
            dmImages.forEach(dmImage -> {
                itemResponse.setImgUrl(dmImage.getImgUrl());
            });
            itemResponses.add(itemResponse);
        });

        return VoUtil.returnSuccess("0000",itemResponses);
    }

    /**
     * 横向导航
     * @return
     */
    public CommonResponse<List<ItemType>>lineNavList(){
        List<ItemType> navList = dmItemClient.lineNavList();
        return VoUtil.returnSuccess("0000",navList);
    }



    /**
     *今日推荐
     * @author
     */
    public CommonResponse<List<ItemResponse>>recommendList(){
        List<Item> isRecommendList = dmItemClient.itemIsRecommendList();
        List<ItemResponse> itemResponses = new ArrayList<>();
        isRecommendList.forEach(item->{
            ItemResponse itemResponse = new ItemResponse();
            Image dmImage = dmImageClient.itemIsRecommendList(item.getId());
            if(dmImage!=null){
                itemResponse.setImgUrl(dmImage.getImgUrl());
            }
            itemResponse.setMinPrice(item.getMinPrice());
            itemResponse.setId(item.getId());
            itemResponse.setItemName(item.getItemName());
            itemResponses.add(itemResponse);
        });
        return VoUtil.returnSuccess("0000",itemResponses);
    }
    /**
     * 即将预售
     * @return
     */
    public CommonResponse<List<ItemResponse>>sellList(){
        List<Item> isRecommendList = dmItemClient.itemStateList();
        List<ItemResponse> itemResponses = new ArrayList<>();
        isRecommendList.forEach(item->{
            ItemResponse itemResponse = new ItemResponse();
            Image dmImage = dmImageClient.itemIsRecommendList(item.getId());
            if(dmImage!=null){
                itemResponse.setImgUrl(dmImage.getImgUrl());
            }
            itemResponse.setMinPrice(item.getMinPrice());
            itemResponse.setId(item.getId());
            itemResponse.setItemName(item.getItemName());
            itemResponses.add(itemResponse);
        });
        return VoUtil.returnSuccess("0000",itemResponses);
    }


    //floor楼层
    public CommonResponse<List<FloorResponse>>floor(){
        List<ItemType> dmItemTypeList = dmItemClient.findByParent(0L);
        List<FloorResponse>floorResponses = new ArrayList<>();

        dmItemTypeList.forEach(dmItemType -> {
            int i=0;
            FloorResponse floorResponse = new FloorResponse();
            List<Item> items = dmItemClient.findById(dmItemType.getId());
            floorResponse.setItemTypeName(dmItemType.getAliasName());
            floorResponse.setItemTypeId(dmItemType.getId());
            floorResponse.setFloor(i++);
            floorResponse.setIndex(i++);
            List<ItemResponse> itemResponseList =new ArrayList<>();
            items.forEach(dmItem -> {
                ItemResponse itemResponse =new ItemResponse();
                BeanUtils.copyProperties(dmItem,itemResponse);
                Cinema cinema = dmItemClient.findCinemaById(dmItem.getCinemaId());
                BeanUtils.copyProperties(cinema,itemResponse);
                Image dmImage = dmImageClient.floorImage(dmItem.getId());
                if (dmImage!= null){
                    itemResponse.setImgUrl(dmImage.getImgUrl());
                }
                itemResponse.setEndDate(dmItem.getEndTime());
                itemResponse.setStartDate(dmItem.getStartTime());
                itemResponseList.add(itemResponse);
            });
            floorResponse.setItems(itemResponseList);
            floorResponses.add(floorResponse);
        });
        return VoUtil.returnSuccess("0000",floorResponses);
    }


    //排行分类
    public CommonResponse<List<ItemResponse>>seniority(Map<String,Long> map){
        List<Item> items = dmItemClient.findById(map.get("itemTypeId"));
        List<ItemResponse> itemResponses = new ArrayList<>();
        items.forEach(dmItem -> {
            ItemResponse itemResponse = new ItemResponse();
            BeanUtils.copyProperties(dmItem,itemResponse);
            Cinema cinema = dmItemClient.findCinemaById(dmItem.getCinemaId());
            itemResponse.setAreaName(cinema.getAreaName());
            itemResponse.setAreaId(cinema.getAreaId());
            itemResponses.add(itemResponse);
        });

        return VoUtil.returnSuccess("0000",itemResponses);
    }



}
