package com.alibaba.dm.controller;

import com.alibaba.dm.dao.LinkUserDao;
import com.alibaba.dm.entity.user.LinkUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class LinkUserController {
    @Resource
    private LinkUserDao linkUserDao;



    //根据用户Id返回所有的购票人
    @PostMapping("/user/queryLinkUser/{userId}")
    public List<LinkUser> queryLinkUser(@PathVariable("userId") Long userId){
        QueryWrapper<LinkUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        List<LinkUser> dmLinkUsers = linkUserDao.selectList(queryWrapper);
        return dmLinkUsers;
    }


    //新增购票人
    @PostMapping("/user/addLinkUser")
    public void addLinkUser(@RequestBody() LinkUser dmLinkUser){
        linkUserDao.insert(dmLinkUser);
    }

    //验证购票人是否存在
    @PostMapping("/user/queryLinkUserByIdCard")
    public LinkUser queryLinkUserByIdCard(@RequestParam() String idCard){
        QueryWrapper<LinkUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("idCard",idCard);
        return linkUserDao.selectOne(queryWrapper);
    }

    //根据ID删除购票人
    @PostMapping("/user/deleteticketbuyer/{linkId}")
    public int deleteticketbuyer(@PathVariable("linkId")Long linkId){
        return linkUserDao.deleteById(linkId);
    }

    //根据ID返回购票人信息
    @PostMapping("/user/queryLinkUserById/")
    public LinkUser queryLinkUserById(@RequestParam("parseLong") long parseLong){
        return linkUserDao.selectById(parseLong);
    }

}
