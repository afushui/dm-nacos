package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class orderTermVo {

    private Long orderType;
    private Long orderTime;
    private String keyword;
    private Long userId;
}
