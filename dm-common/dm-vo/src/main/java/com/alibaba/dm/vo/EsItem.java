package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class EsItem {

    private Integer areaId;
    private Integer currentPage;
    private String endTime;
    private Integer itemTypeId1;
    private Integer itemTypeId2;
    private String keyword;
    private Integer pageSize;
    private String sort;
    private String startTime;
}
