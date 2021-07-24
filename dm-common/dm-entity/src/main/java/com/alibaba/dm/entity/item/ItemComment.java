package com.alibaba.dm.entity.item;

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
@TableName("dm_item_comment")
public class ItemComment extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long itemId;
    private Long userId;
    private Long score;
    private String content;
}
