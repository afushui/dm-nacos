package com.alibaba.dm.entity.item;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "dm_cinema_seat")
public class CinemaSeat extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long x;
    private Long y;
    private Long areaLevel;
    private Long cinemaId;
    private Long status;
    private Long sort;
}
