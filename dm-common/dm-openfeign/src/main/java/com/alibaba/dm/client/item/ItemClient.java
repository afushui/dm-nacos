package com.alibaba.dm.client.item;

import com.alibaba.dm.entity.item.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "dm-item-provider")
@RequestMapping("/item")
public interface ItemClient {

    @PostMapping("/isBanner/{itemType1Id}")
    public List<Item> itemIsBannerList(@PathVariable("itemType1Id")Long itemType1Id);

    ///今日推荐
    @PostMapping("/isRecommend")
    public List<Item> itemIsRecommendList();

    //即将预售state=2预售/预订
    @PostMapping("/state")
    public List<Item> itemStateList();

    @PostMapping("/carouse/isBanner")
    public List<Item>carouselIsBanner();

    @PostMapping("/findByTypeId/{itemType1Id}")
    public List<Item> findById(@PathVariable("itemType1Id") Long itemType1Id);


    @PostMapping("/seniorityList/{itemTypeId}")
    public List<Item>seniorityList(@PathVariable("itemTypeId") Long itemTypeId);


    //查询商品ID
    @PostMapping("/findByItemId/{itemId}")
    public Item findByItemId(@PathVariable("itemId")Long itemId);

    //推荐
    @PostMapping("/findByItemType1Id")
    public List<Item> findByItemType1Id(@RequestParam("itemTypeId") Long itemTypeId, @RequestParam("limit") Integer limit);


    //推荐
    @PostMapping("/findByItemType2Id")
    public List<Item> findByItemType2Id(@RequestParam("itemTypeId") Long itemTypeId, @RequestParam("limit") Integer limit);






    @PostMapping("/parent/{parent}")
    public List<ItemType> findByParent(@PathVariable("parent") Long parent);

    @PostMapping("/itemType2Name")
    public List<ItemType>lineNavList();


    //根据Id返回剧场信息
    @PostMapping("/findById/{id}")
    public Cinema findCinemaById(@PathVariable("id") Long id);

    //返回所有剧场地址
    @PostMapping("/querycity")
    public List<Cinema> queryCity();


    //
    @PostMapping("/dmCinemaSeat/{cinemaId}")
    public List<CinemaSeat> getDmCinemaSeat(@PathVariable("cinemaId") Long cinemaId);


    //商品详情页  根据商品id查询剧评
    @PostMapping("/findByItemId")
    public List<ItemComment> commentByItemId(@RequestParam Long itemId);


    // 商品详情页  添加剧评
    @PostMapping("/addItemComment")
    public void addItemComment(@RequestBody Map<String,Object> map);

}
