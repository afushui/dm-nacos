package com.alibaba.dm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerByItemIdResponse {

    private Long id;
    private String title;
    private Long itemId;
    private String startTime;
    private String endTime;
    private Long cinemaId;


}