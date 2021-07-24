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
public class ItemCinemaService {

    @Autowired
    ItemClient dmItemClient;
    @Autowired
    ImageClient imageClient;


    public CommonResponse<List<DmCinemaCity>> queryCityList(){
        List<DmCinemaCity> cinemaCityList = new ArrayList<>();
        List<Cinema> dmCinemas = dmItemClient.queryCity();
        dmCinemas.forEach(dmCinema -> {
            DmCinemaCity cinemaCity = new DmCinemaCity();
            cinemaCity.setId(dmCinema.getId());
            cinemaCity.setName(dmCinema.getAreaName());
            cinemaCityList.add(cinemaCity);
        });
        return VoUtil.returnSuccess("0000",cinemaCityList);
    }

    public CommonResponse<List<ItemTypeList>>queryItemType(Map<String,Object> map){
        List<ItemTypeList> itemTypeLists = new ArrayList<>();
        List<ItemType> dmItemTypeList= null;
        for (String s : map.keySet()) {
            if (s.equals("parent")){
                dmItemTypeList=  dmItemClient.findByParent(Long.parseLong(map.get("parent").toString()));
            }else if (s.equals("param")){
                dmItemTypeList= dmItemClient.findByParent(Long.parseLong(map.get("param").toString()));
            }
        }
        dmItemTypeList.forEach(dmItemType -> {
            ItemTypeList typeList =new ItemTypeList();
            typeList.setId(dmItemType.getId());
            typeList.setItemType(dmItemType.getItemType());
            typeList.setLevel(dmItemType.getLevel());
            typeList.setParent(dmItemType.getParent());
            typeList.setAliasName(dmItemType.getAliasName());
            itemTypeLists.add(typeList);
        });
        return  VoUtil.returnSuccess("0000",itemTypeLists);
    }




    //列表页 猜你喜欢 guesslike
    public CommonResponse<List<GuessYouLikeResponse>>guesslike(Long itemTypeId, Integer limit){
        List<GuessYouLikeResponse> guessYouLikeResponses = new ArrayList<>();
        List<Item> itemList = null;
        if (itemTypeId>9){
            itemList=  dmItemClient.findByItemType2Id(itemTypeId, limit);
        }else {
            itemList= dmItemClient.findByItemType1Id(itemTypeId, limit);
        }

        itemList.forEach(dmItem -> {
            GuessYouLikeResponse guessYouLikeResponse =new GuessYouLikeResponse();
            BeanUtils.copyProperties(dmItem,guessYouLikeResponse);
            Cinema cinema = dmItemClient.findCinemaById(dmItem.getCinemaId());
            guessYouLikeResponse.setAddress(cinema.getAddress());
            guessYouLikeResponse.setAreaId(cinema.getAreaId());
            guessYouLikeResponse.setAreaName(cinema.getAreaName());
            guessYouLikeResponse.setStartDate(dmItem.getStartTime().toString());
            guessYouLikeResponse.setEndDate(dmItem.getEndTime().toString());
            guessYouLikeResponse.setMinPrice(dmItem.getMinPrice());
            Image itemImage = imageClient.getItemImage(dmItem.getId());
            if (itemImage!=null) {
                guessYouLikeResponse.setImgUrl(itemImage.getImgUrl());
            }
            guessYouLikeResponses.add(guessYouLikeResponse);
        });
        return VoUtil.returnSuccess("0000",guessYouLikeResponses);

    }


}
