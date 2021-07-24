package com.alibaba.dm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDescResponse {
    private Long id;
    private String abstractMessage;
    private String itemName;
    private Long itemType1Id;
    private  String itemType1Name;
    private Long itemType2Id;
    private String itemType2Name;
    private Long areaId;
    private String areaName;
    private String startTime;
    private String endTime;
    private String state;
    private String basicDescription;
    private String projectDescription;
    private String imgUrl;
    private Long cinemaId;
    private String address;
    private String latitude;
    private String longitude;
    private Double avgScore;
    private Long commentCount;
}
