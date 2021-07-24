package com.alibaba.dm.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleIdSeatResponse {
    private Long cinemaId;
    private List<String> seatArray=new ArrayList<>();
}
