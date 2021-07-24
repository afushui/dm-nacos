package com.alibaba.dm.controller;

import com.alibaba.dm.dao.UserDao;;
import com.alibaba.dm.entity.user.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    private UserDao userDao;


    //根据手机号返回用户对象
    @PostMapping(path = "/user/valid/{phone}")
    public User findUserByPhone(@PathVariable("phone") String phone){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        return userDao.selectOne(queryWrapper);
    }

    //新增用户
    @PostMapping(path = "/user/insert")
    public int addUser(@RequestBody User user){
        return userDao.insert(user);
    }

    //修改用户
    @PostMapping("/user/upUser")
    public void upUser(@RequestBody User user){ userDao.updateById(user);}

    //根句用户ID返货用户信息
    @PostMapping("/user/findById")
    public User findById(@RequestParam Long userId){
        return userDao.selectById(userId);
    }


}
