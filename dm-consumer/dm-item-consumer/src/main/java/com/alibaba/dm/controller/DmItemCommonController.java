package com.alibaba.dm.controller;

import com.alibaba.dm.service.ItemCinemaService;
import com.alibaba.dm.service.ItemService;
import com.alibaba.dm.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class DmItemCommonController {
    @Autowired
    ItemService itemService;
    @Autowired
    ItemCinemaService itemCinemaService;
    @PostMapping("/getItems")
    CommonResponse<ItemDescResponse> getItems(@RequestBody Map<String,Object> map){ return itemService.queryItemDetail(Long.parseLong(map.get("id").toString())); }

    @PostMapping("querycity")
    CommonResponse<List<DmCinemaCity>> queryCityList(){
        return itemCinemaService.queryCityList();
    }
    @PostMapping("sortgoods")
    CommonResponse<List<ItemTypeList>>queryItemType(@RequestBody Map<String,Object> map){
        return itemCinemaService.queryItemType(map);
    }
    @PostMapping("guesslike")
    CommonResponse<List<GuessYouLikeResponse>>guesslike(@RequestBody Map<String,Object> map){
        return itemCinemaService.guesslike(Long.parseLong(map.get("itemTypeId").toString()),4);
    }
    @PostMapping("querygoodsinfos")
    CommonResponse<PageResult> queryItemList(@RequestBody()EsItem esItem){
       return itemService.queryItemList(esItem);
    }
}