package com.alibaba.dm.entity.order;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("dm_order_link_user")
public class OrderLinkUser extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderId;
    private Long linkUserId;
    private String linkUserName;
    private Long x;
    private Long y;
    private Double price;
}
