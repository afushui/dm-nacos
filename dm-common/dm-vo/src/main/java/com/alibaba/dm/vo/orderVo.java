package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class orderVo {

    private Long id;
    private String orderNo;
    private String itemName;
    private double totalAmount;
    private Long  userId;
    private Long orderType;
    private Long num;
    private Double unitPrice;
    private String sellTime;
}
