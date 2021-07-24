package com.alibaba.dm.controller;

import com.alibaba.dm.dao.CinemaDao;
import com.alibaba.dm.entity.item.Cinema;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class CinemaController {
    @Autowired
    private CinemaDao cinemaDao;


    //根据Id返回剧场信息
    @PostMapping("/findById/{id}")
    public Cinema findCinemaById(@PathVariable("id") Long id){
        return cinemaDao.selectById(id);
    }

    //返回所有剧场地址
    @PostMapping("/querycity")
    public List<Cinema> queryCity(){
        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();
        return cinemaDao.selectList(queryWrapper);
    }

}
