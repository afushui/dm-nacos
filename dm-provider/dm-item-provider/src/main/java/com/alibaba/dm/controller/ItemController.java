package com.alibaba.dm.controller;

import com.alibaba.dm.dao.ItemDao;
import com.alibaba.dm.entity.item.Item;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("item")
public class ItemController {
    @Resource
    private ItemDao itemDao;

    @PostMapping("/isBanner/{itemType1Id}")
    public List<Item>itemIsBannerList(@PathVariable("itemType1Id")Long itemType1Id){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isBanner",1);
        queryWrapper.eq("itemType1Id",itemType1Id);
        IPage<Item> page=new Page<>(1,5);
        return itemDao.selectPage(page,queryWrapper).getRecords();
    }

    //今日推荐
    @PostMapping("/isRecommend")
    public List<Item> itemIsRecommendList(){
        QueryWrapper<Item>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isRecommend",1);
        Page<Item> page=new Page(1,6);
        return itemDao.selectPage(page,queryWrapper).getRecords();
    }


    //即将预售state=2预售/预订
    @PostMapping("/state")
    public List<Item> itemStateList(){
        QueryWrapper<Item>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",2);
        Page<Item> page=new Page(1,6);
        return itemDao.selectPage(page,queryWrapper).getRecords();
    }


    @PostMapping("/carouse/isBanner")
    public List<Item>carouselIsBanner(){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isBanner",1);
        IPage<Item> page=new Page<>(1,5);
        return itemDao.selectPage(page,queryWrapper).getRecords();
    }


    @PostMapping("/findByTypeId/{itemType1Id}")
    public List<Item> findById(@PathVariable("itemType1Id") Long itemType1Id){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemType1Id",itemType1Id);
        queryWrapper.orderByDesc("avgScore");
        Page<Item> page =new Page<>(1,5);
        return itemDao.selectPage(page,queryWrapper).getRecords();
    }

    @PostMapping("/seniorityList/{itemTypeId}")
    public List<Item>seniorityList(@PathVariable("itemTypeId") Long itemTypeId){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemType1Id",itemTypeId);
        queryWrapper.eq("isBanner",1);
        Page<Item>page =new Page<>(1,8);
        return itemDao.selectPage(page,queryWrapper).getRecords();
    }

    //查询商品ID
    @PostMapping("/findByItemId/{itemId}")
    public Item findByItemId(@PathVariable("itemId")Long itemId){
        return itemDao.selectById(itemId);
    }


    //推荐
    @PostMapping("/findByItemType1Id")
    public List<Item> findByItemType1Id(@RequestParam("itemTypeId") Long itemTypeId, @RequestParam("limit") Integer limit){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemType1Id",itemTypeId);
        Page<Item> page =new Page<>(0,limit);
        return itemDao.selectPage(page,queryWrapper).getRecords();
    }

    //推荐
    @PostMapping("/findByItemType2Id")
    public List<Item> findByItemType2Id(@RequestParam("itemTypeId") Long itemTypeId, @RequestParam("limit") Integer limit){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("itemType2Id",itemTypeId);
        IPage<Item> page =new Page<>(0,limit);
        return itemDao.selectPage(page,queryWrapper).getRecords();
    }

}
