package com.alibaba.dm.controller;

import com.alibaba.dm.dao.OrderDao;
import com.alibaba.dm.entity.order.Order;
import com.alibaba.dm.vo.orderTermVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class OrderController {
    @Resource
    private OrderDao orderDao;

    //添加订单
    @PostMapping(path = "/addOrder")
    public int addOrder(@RequestBody Order order){
        return orderDao.insert(order);
    }

    //删除订单
    @PostMapping(path = "/delOrder/{orderNo}")
    public int delOrder(@PathVariable("orderNo") String orderNo){
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderNo",orderNo);
        return orderDao.delete(queryWrapper);
    }

    //根据Id删除订单
    @PostMapping("delByOrderId/{orderId}")
    public int delByOrderId(@PathVariable("orderId") Long orderId){
        return orderDao.deleteById(orderId);
    }
    //通过订单号查询订单
    @PostMapping(path = "/findByOrderNo/{orderNo}")
    public Order findByOrderNo(@PathVariable("orderNo") String orderNo){
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("orderNo",orderNo);
        return orderDao.selectOne(queryWrapper);
    }

    //多条件查询订单
    @PostMapping(path = "/queryorderlist")
    public List<Order> queryorderlist(@RequestBody() orderTermVo orderTermVo){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if(orderTermVo!=null){
            LocalDateTime time = LocalDateTime.now();
            queryWrapper.and(wrapper -> wrapper.like("orderNo", orderTermVo.getKeyword()).or().like("itemName", orderTermVo.getKeyword()));
            if(orderTermVo.getUserId()!=null){
                queryWrapper.eq("userId",orderTermVo.getUserId());
            }
            if(orderTermVo.getOrderTime() !=null &&  orderTermVo.getOrderTime() == 1){
                queryWrapper.between("createdTime",time.plusYears(-1L),time);
            }else if(orderTermVo.getOrderTime() !=null &&  orderTermVo.getOrderTime() == 2){
                queryWrapper.between("createdTime",time.plusMonths(-3L),time);
            }
            if(orderTermVo.getOrderType()!=null && orderTermVo.getOrderType() == -1){
                queryWrapper.eq("orderType",orderTermVo.getOrderType());  //已取消
            }else if(orderTermVo.getOrderType()!=null && orderTermVo.getOrderType() == 0){
                queryWrapper.eq("orderType",orderTermVo.getOrderType());  //待支付
            }else if(orderTermVo.getOrderType()!=null && orderTermVo.getOrderType() == 2){
                queryWrapper.eq("orderType",orderTermVo.getOrderType());  //完成
            }

        }
        return orderDao.selectList(queryWrapper);
    }

    //修改状态
    @PostMapping("/updateOrderStatus")
    public  void updateOrderStatus(@RequestBody() Order dmOrder){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("orderNo",dmOrder.getOrderNo());
        orderDao.update(dmOrder,wrapper);
    }
}
