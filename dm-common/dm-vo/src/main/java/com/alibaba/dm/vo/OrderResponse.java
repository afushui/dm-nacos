package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class OrderResponse {
   private String orderNo;
   private String itemName;
   private String seatNames; //
   private Long seatCount;
   private Double totalAmount;

}
