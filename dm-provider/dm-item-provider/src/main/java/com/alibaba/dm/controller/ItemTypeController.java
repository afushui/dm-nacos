package com.alibaba.dm.controller;

import com.alibaba.dm.dao.ItemTypeDao;
import com.alibaba.dm.entity.item.ItemType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemTypeController {
    @Resource
    private ItemTypeDao itemTypeDao;

    @PostMapping("/parent/{parent}")
    public List<ItemType> findByParent(@PathVariable("parent") Long parent) {
        QueryWrapper<ItemType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent", parent);
        return itemTypeDao.selectList(queryWrapper);
    }

    @PostMapping("/itemType2Name")
    public List<ItemType>lineNavList(){
        QueryWrapper<ItemType> queryWrapper = new QueryWrapper<>();
        IPage<ItemType> page=new Page<>(1,5);
        return itemTypeDao.selectPage(page,queryWrapper).getRecords();
    }
}
