package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class CreateOrderRequest {

    //下单用户Id
    private Long userId;
    //排期ID
    private Long schedulerId;
    //剧集Id(冗余)
    private Long itemId;
    //下单关联用户
    private String linkIds;
    //剧场ID
    private Long cinemaId;
    //0:WEB端 1:手机端 2:其他客户端
    private Integer sourceType;
    //是否需要发票（0：不需要 1：需要）
    private Integer isNeedInvoice;
    //发票类型（0：个人 1：公司）
    private Integer invoiceType;
    //发票抬头
    private String invoiceHead;
    //发票号
    private String invoiceNo;
    //是否需要保险(0：不需要 1:需要)
    private Integer isNeedInsurance;//20
    //座位坐标（x1_y1,x2_y2,x3_y3）
    private String seatPositions;


}
