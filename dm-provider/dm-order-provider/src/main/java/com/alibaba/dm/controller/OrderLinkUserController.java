package com.alibaba.dm.controller;

import com.alibaba.dm.dao.OrderLinkUserDao;
import com.alibaba.dm.entity.order.OrderLinkUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class OrderLinkUserController {
    @Resource
    private OrderLinkUserDao orderLinkUserDao;

    //根据 orderId获取订单购票人信息
    @PostMapping("/order/findByOrderId/{orderId}")
    public List<OrderLinkUser> findByOrderId(@PathVariable("orderId")Long orderId){
        QueryWrapper<OrderLinkUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderId",orderId);
        return orderLinkUserDao.selectList(queryWrapper);
    }

    //查询购票联系人信息======
    @PostMapping(path = "/findLinkUserById")
    public OrderLinkUser findLinkUserById(@RequestParam("parseLong") long parseLong){
        return orderLinkUserDao.selectById(parseLong);
    }

    //添加购票人======
    @PostMapping(path = "/addOrderLinkUser")
    void addOrderLinkUser(@RequestBody OrderLinkUser orderLinkUser){
        orderLinkUserDao.insert(orderLinkUser);
    }

}
