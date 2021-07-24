package com.alibaba.dm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchedulerPriceResponse {

    private Long id;
    private Long scheduleId;
    private Double price;
    private Long isHaveSeat;

}