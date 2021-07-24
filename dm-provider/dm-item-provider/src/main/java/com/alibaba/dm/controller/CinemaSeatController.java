package com.alibaba.dm.controller;

import com.alibaba.dm.dao.CinemaSeatDao;
import com.alibaba.dm.entity.item.CinemaSeat;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/item")
public class CinemaSeatController {
    @Resource
    private CinemaSeatDao cinemaSeatDao;

    //
    @PostMapping("/dmCinemaSeat/{cinemaId}")
    public List<CinemaSeat> getDmCinemaSeat(@PathVariable("cinemaId") Long cinemaId){
        QueryWrapper<CinemaSeat> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cinemaId",cinemaId);
        return cinemaSeatDao.selectList(queryWrapper);
    }





}
