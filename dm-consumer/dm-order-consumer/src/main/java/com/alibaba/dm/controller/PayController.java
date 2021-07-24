package com.alibaba.dm.controller;


import com.alibaba.dm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PayController {
    @Autowired
    OrderService orderService;
    @RequestMapping("/pay")
    @ResponseBody
    public String pay(@RequestParam("orderNo")  String orderNo)  {
        return orderService.zhiFuBao(orderNo);
    }

    @RequestMapping("/alipayReturnNotice")
    public String shaXiangZhiFu(Model model, HttpServletRequest request, HttpServletResponse response){
        return orderService.alipayReturnNotice(model, request, response);
    }
}
