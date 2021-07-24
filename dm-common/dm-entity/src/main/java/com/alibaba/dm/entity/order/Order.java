package com.alibaba.dm.entity.order;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("dm_order")
public class Order extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String orderNo;
    private Long userId;
    private Long schedulerId;
    private Long itemId;
    private String itemName;
    private String wxTradeNo;
    private String aliTradeNo;
    private Long orderType;
    private String payType;
    private Long totalCount;
    private Long area;
    private Long sourceType;
    private Long isNeedInvoice;
    private Long invoiceType;
    private String invoiceHead;
    private String invoiceNo;
    private Long isNeedInsurance;
    private Double totalAmount;
    private Double insuranceAmount;
}
