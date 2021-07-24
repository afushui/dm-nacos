package com.alibaba.dm.controller;

import com.alibaba.dm.dao.ItemCommentDao;
import com.alibaba.dm.entity.item.ItemComment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemCommentController {
    @Resource
    private ItemCommentDao itemCommentDao;


    //商品详情页  根据商品id查询剧评
    @PostMapping("/findByItemId")
    public List<ItemComment> commentByItemId(@RequestParam Long itemId){
        QueryWrapper<ItemComment> queryWrapper =new QueryWrapper();
        queryWrapper.eq("id",itemId);
        return itemCommentDao.selectList(queryWrapper);
    }
    // 商品详情页  添加剧评
    @PostMapping("/addItemComment")
    public void addItemComment(@RequestBody Map<String,Object> map){
        ItemComment itemComment=new ItemComment();
        itemComment.setItemId((long) map.get("itemId"));
        itemComment.setContent(map.get("comment").toString());
        itemComment.setScore(Long.parseLong(map.get("score").toString()));
        itemComment.setUserId((long) map.get("userId"));
        itemCommentDao.insert(itemComment);
    }
}
