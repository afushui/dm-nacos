package com.alibaba.dm.service;

import com.alibaba.dm.client.base.ImageClient;
import com.alibaba.dm.client.item.ItemClient;
import com.alibaba.dm.entity.base.Image;
import com.alibaba.dm.entity.item.Item;

import com.alibaba.dm.vo.CommonResponse;
import com.alibaba.dm.vo.QinZiCarousel;
import com.alibaba.dm.vo.VoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemQinZiService {

    @Autowired
    ItemClient dmItemClient;
    @Autowired
    ImageClient dmImageClient;
    //亲子轮播图  getCarouselData {itemTypeId}
    public CommonResponse<List<QinZiCarousel>> getCarouselData(Long itemTypeId){
        List<QinZiCarousel> qinZiCarousels = new ArrayList<>();
        List<Item> itemList = dmItemClient.findByItemType1Id(itemTypeId, 5);

        itemList.forEach(dmItem -> {
            QinZiCarousel qinZiCarousel =new QinZiCarousel();
            qinZiCarousel.setId(dmItem.getId());
            qinZiCarousel.setItemName(dmItem.getItemName());
            qinZiCarousel.setMinPrice(dmItem.getMinPrice());
            Image itemImage = dmImageClient.findByItemImage(dmItem.getId());
            if (itemImage!= null){
                qinZiCarousel.setImgUrl(itemImage.getImgUrl());
            }
            qinZiCarousels.add(qinZiCarousel);
        });
        return VoUtil.returnSuccess("0000",qinZiCarousels);
    }
}
