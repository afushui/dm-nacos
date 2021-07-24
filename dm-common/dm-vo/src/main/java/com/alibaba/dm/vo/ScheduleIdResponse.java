package com.alibaba.dm.vo;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleIdResponse {
    private Long scheduleId;
    private  Long cinemaId;
    private List<SeatPriceListResponse> seatPriceList;
    private List<SeatInfoListResponse> seatInfoList;
}
