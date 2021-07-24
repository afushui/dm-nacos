package com.alibaba.dm.entity.item;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("dm_item")
public class Item extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String itemName;
    private String abstractMessage;
    private Long itemType1Id;
    private String itemType1Name;
    private Long itemType2Id;
    private String itemType2Name;
    private Integer state;
    private String basicDescription;
    private String projectDescription;
    private String reminderDescription;
    private String longitude;
    private String latitude;
    private Integer isBanner;
    private Integer isRecommend;
    private Double avgScore;
    private Integer commentCount;
    private Long cinemaId;
    private Date startTime;
    private Date endTime;
    private Double minPrice;
    private Double maxPrice;
    private Integer ageGroup;
}
