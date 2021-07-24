package com.alibaba.dm.controller;

import com.alibaba.dm.entity.item.ItemType;
import com.alibaba.dm.service.ItemTypeService;
import com.alibaba.dm.vo.CommonResponse;
import com.alibaba.dm.vo.FloorResponse;
import com.alibaba.dm.vo.ItemResponse;
import com.alibaba.dm.vo.ItemTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/item")
public class DmItemTypeController {

    @Autowired
    ItemTypeService itemTypeService;

    @PostMapping("/nav")
    CommonResponse<List<ItemTypeResponse>> test() { return itemTypeService.navList();}
    @PostMapping("/lineNav")
    CommonResponse<List<ItemType>>lineNav(){ return itemTypeService.lineNavList(); }
    @PostMapping("/recommend")
    CommonResponse<List<ItemResponse>>recommend(){ return itemTypeService.recommendList();}
    @PostMapping("/sell")
    CommonResponse<List<ItemResponse>>sell(){return itemTypeService.sellList();}
    @PostMapping("/carousel")
    CommonResponse<List<ItemResponse>>carousel(){ return itemTypeService.carousel();}
    @PostMapping("/floor")
    public CommonResponse<List<FloorResponse>>floor(){return  itemTypeService.floor();}
    @PostMapping("/seniority")
    public CommonResponse<List<ItemResponse>>seniority(@RequestBody() Map<String,Long> map){ return itemTypeService.seniority(map);}



}
