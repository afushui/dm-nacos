package com.alibaba.dm.controller;


import com.alibaba.dm.service.OrderService;
import com.alibaba.dm.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/submitorder")
    CommonResponse<Map> createOrder(@RequestBody() CreateOrderRequest orderRequest){
      return orderService.createOrder(orderRequest);
    }

    @PostMapping("/confirmpay")
    CommonResponse<OrderResponse>confirmpay(@RequestBody() Map<String,String> map){
       return orderService.queryOrderByOrderNo(map.get("orderNo"));
    }

    @PostMapping("/queryorderlist")
    public CommonResponse<List<orderVo>> queryorderlist(@RequestBody() orderTermVo orderTermVo, HttpServletRequest request){
        return orderService.queryorderlist(orderTermVo,request);
    }


}
