package com.alibaba.dm.client.order;

import com.alibaba.dm.entity.order.Order;
import com.alibaba.dm.entity.order.OrderLinkUser;
import com.alibaba.dm.vo.orderTermVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("dm-order-provider")
public interface OrderClient {


    //添加订单
    @PostMapping(path = "/addOrder")
    public int addOrder(@RequestBody Order order);


    //删除订单
    @PostMapping(path = "/delOrder/{orderNo}")
    public int delOrder(@PathVariable("orderNo") String orderNo);


    //通过订单号查询订单
    @PostMapping(path = "/findByOrderNo/{orderNo}")
    public Order findByOrderNo(@PathVariable("orderNo") String orderNo);


    //多条件查询订单
    @PostMapping(path = "/queryorderlist")
    public List<Order> queryorderlist(@RequestBody() orderTermVo orderTermVo);


    //修改状态
    @PostMapping("/updateOrderStatus")
    public  void updateOrderStatus(@RequestBody() Order dmOrder);


    //根据 orderId获取订单购票人信息
    @PostMapping("/order/findByOrderId/{orderId}")
    public List<OrderLinkUser> findByOrderId(@PathVariable("orderId")Long orderId);

    //查询购票联系人信息======
    @PostMapping(path = "/findLinkUserById")
    public OrderLinkUser findLinkUserById(@RequestParam("parseLong") long parseLong);


    //添加购票人======
    @PostMapping(path = "/addOrderLinkUser")
    void addOrderLinkUser(@RequestBody OrderLinkUser orderLinkUser);


    //根据Id删除订单
    @PostMapping("delByOrderId/{orderId}")
    public int delByOrderId(@PathVariable("orderId") Long orderId);
}
