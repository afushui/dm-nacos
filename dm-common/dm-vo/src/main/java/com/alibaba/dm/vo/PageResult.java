package com.alibaba.dm.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult {

    private Integer currentPage;
    private Integer pageCount;
    private Integer pageSize;
    private List rows;
}
