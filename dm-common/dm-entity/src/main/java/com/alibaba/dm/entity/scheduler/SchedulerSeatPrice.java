package com.alibaba.dm.entity.scheduler;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "dm_scheduler_seat_price")
public class SchedulerSeatPrice extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    //价格
    private Double price;
    //区域级别(1:A;2:B;3:C)
    private Integer areaLevel;
    //排期Id
    private Long scheduleId;
    //座位存量
    private Integer seatNum;
}