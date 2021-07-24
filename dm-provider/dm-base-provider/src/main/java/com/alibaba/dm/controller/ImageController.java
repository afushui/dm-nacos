package com.alibaba.dm.controller;

import com.alibaba.dm.dao.ImageDao;
import com.alibaba.dm.entity.base.Image;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ImageController {
    @Resource
    private ImageDao imageDao;

    //相关热门图片
    @PostMapping("/image/item/{itemId}")
    public Image itemIsBannerList(@PathVariable("itemId")Long itemId){
        QueryWrapper<Image>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2);
        queryWrapper.eq("targetID",itemId);
        return imageDao.selectOne(queryWrapper);
    }

    //轮播图
    @PostMapping("/image/item/carousel/{itemId}")
    public List<Image> itemCarouselList(@PathVariable("itemId")Long itemId){
        QueryWrapper<Image>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",1);
        queryWrapper.eq("targetId",itemId);
        return imageDao.selectList(queryWrapper);
    }

    //今日推荐
    @PostMapping("/image/itemIsRecommend/{itemId}")
    public Image itemIsRecommendList(@PathVariable("itemId")Long itemId){
        QueryWrapper<Image>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2);
        queryWrapper.eq("targetID",itemId);
        return imageDao.selectOne(queryWrapper);
    }

    //即将预售
    @PostMapping("/itemSell/{itemId}")
    public Image itemSellList(@PathVariable("itemId")Long itemId){
        QueryWrapper<Image>queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2);
        queryWrapper.eq("targetID",itemId);
        return imageDao.selectOne(queryWrapper);
    }

    //楼层图片
    @PostMapping("/itemFloor/{targetId}")
    public Image floorImage(@PathVariable("targetId") Long targetId) {
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2);
        queryWrapper.eq("category",1);
        queryWrapper.eq("targetId",targetId);
        Image image = imageDao.selectOne(queryWrapper);
        return image;
    }

    //用户登陆头像
    @PostMapping("/image/loginImage/{imageId}")
    public Image loginImage(@PathVariable("imageId")Long imageId){
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category",0);
        queryWrapper.eq("targetId",imageId);
        Image dmImage = imageDao.selectOne(queryWrapper);
        return dmImage;
    }
    //
    @PostMapping("/image/itemImage/{itemId}")
    public Image findByItemImage(@PathVariable("itemId")Long itemId){
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",2);
        queryWrapper.eq("targetId",itemId);
        Image dmImage =imageDao.selectOne(queryWrapper);
        return dmImage;
    }


    //用户头像
    @PostMapping("/image/userImage/{userId}")
    public Image ByUserImage(@PathVariable("userId")Long userId){
        QueryWrapper<Image> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("targetId",userId);
        Image dmImage = imageDao.selectOne(queryWrapper);
        return dmImage;
    }

}
