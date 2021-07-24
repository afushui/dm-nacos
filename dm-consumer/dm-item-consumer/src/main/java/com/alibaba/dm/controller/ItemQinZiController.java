package com.alibaba.dm.controller;

import com.alibaba.dm.service.ItemQinZiService;
import com.alibaba.dm.vo.CommonResponse;
import com.alibaba.dm.vo.QinZiCarousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ItemQinZiController {
    @Autowired
    ItemQinZiService qinZiService;

    @PostMapping("getCarouselData")
    CommonResponse<List<QinZiCarousel>> getCarouselData(@RequestBody() Map<String,Object>map){
        return qinZiService.getCarouselData(Long.parseLong(map.get("itemTypeId").toString()));
    }

}
