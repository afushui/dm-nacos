package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class ItemTypeList {
    private Long id;
    private String itemType;
    private Long level;
    private Long parent;
    private String aliasName;
}
