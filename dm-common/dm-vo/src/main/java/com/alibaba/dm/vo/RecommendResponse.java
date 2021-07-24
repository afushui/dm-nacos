package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class RecommendResponse {
    private Long id;
    private String itemName;
    private Long areaId;
    private String areaName;
    private String address;
    private String startDate;
    private String endDate;
    private String imgUrl;
    private Double minPrice;
}
