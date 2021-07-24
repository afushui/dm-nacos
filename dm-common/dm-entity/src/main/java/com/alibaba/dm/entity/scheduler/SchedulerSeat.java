package com.alibaba.dm.entity.scheduler;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "dm_scheduler_seat")
public class SchedulerSeat extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long x;
    private Long y;
    private Long areaLevel;
    private Long scheduleId;
    private String orderNo;
    private Long userId;
    private Long status;
    private Long sort;
}
