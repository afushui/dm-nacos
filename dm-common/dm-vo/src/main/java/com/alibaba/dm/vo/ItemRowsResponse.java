package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class ItemRowsResponse {
    private  Long id;
    private String imgUrl;
    private String areaName;
    private String itemName;
    private String abstractMessage;
    private String startTime;
    private String endTime;
    private Double minPrice;
    private Double maxPrice;
    private String address;
    private String latitude;
    private String longitude;

}
