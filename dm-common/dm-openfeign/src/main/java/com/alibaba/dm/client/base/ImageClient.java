package com.alibaba.dm.client.base;

import com.alibaba.dm.entity.base.Image;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "dm-base-provider")
public interface ImageClient {

    //相关热门图片
//相关热门
    @PostMapping("/image/item/{itemId}")
    Image getItemImage(@PathVariable("itemId")Long itemId);
    @PostMapping("/image/item/{itemId}")
    public Image itemIsBannerList(@PathVariable("itemId")Long itemId);

    //轮播图
    @PostMapping("/image/item/carousel/{itemId}")
    public List<Image> itemCarouselList(@PathVariable("itemId")Long itemId);

    //今日推荐
    @PostMapping("/image/itemIsRecommend/{itemId}")
    public Image itemIsRecommendList(@PathVariable("itemId")Long itemId);

    //即将预售
    @PostMapping("/itemSell/{itemId}")
    public Image itemSellList(@PathVariable("itemId")Long itemId);

    //楼层图片
    @PostMapping("/itemFloor/{targetId}")
    public Image floorImage(@PathVariable("targetId") Long targetId);

    //用户登陆头像
    @PostMapping("/image/loginImage/{imageId}")
    public Image loginImage(@PathVariable("imageId")Long imageId);

    @PostMapping("/image/itemImage/{itemId}")
    public Image findByItemImage(@PathVariable("itemId")Long itemId);

    //用户头像
    @PostMapping("/image/userImage/{userId}")
    public Image ByUserImage(@PathVariable("userId")Long userId);
}
