package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class SeatInfoListResponse {
    private Long id;
    private Long x;
    private Long y;
    private String areaLevel;
    private Long cinemaId;
    private Long sort;
    private Long status;
}
