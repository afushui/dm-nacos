package com.alibaba.dm.entity.item;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data@TableName(value = "dm_item_type")
public class ItemType extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String itemType;
    private Long level;
    private Long parent;
    private String aliasName;
    @TableField(value = "`key`")
    private Long key;
}
