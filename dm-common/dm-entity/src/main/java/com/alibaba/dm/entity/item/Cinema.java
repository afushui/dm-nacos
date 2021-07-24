package com.alibaba.dm.entity.item;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_cinema")
public class Cinema extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String address;
    private Long areaId;
    private String areaName;
    private Long xLength;
    private Long yLength;
    private String latitude;
    private String Longitude;
}
