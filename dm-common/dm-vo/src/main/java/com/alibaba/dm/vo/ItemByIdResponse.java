package com.alibaba.dm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemByIdResponse {
    private Integer id;
    private String abstractMessage;
    private String itemName;
    private Integer itemType1Id;
    private String itemType1Name;
    private Integer itemType2Id;
    private String itemType2Name;
    private Integer areaId;
    private String areaName;
    private String startTime;
    private String endTime;
    private String state;
    private String basicDescription;
    private String projectDescription;
    private String imgUrl;
    private Integer cinemaId;
    private String address;
    private String latitude;
    private String longtitude;
    private Float avgScore;
    private Integer commentCount;
}
