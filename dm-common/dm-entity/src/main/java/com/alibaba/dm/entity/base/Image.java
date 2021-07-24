package com.alibaba.dm.entity.base;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("dm_image")
public class Image extends BaseEntity {

    private Long id;
    private String imgUrl;
    private Long targetId;
    private Long type;
    private Long sort;
    private Long category;
}
