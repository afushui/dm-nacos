package com.alibaba.dm.vo;

import lombok.Data;

@Data
public class LinkUserResponse {
    private Long linkId;
    private String name;
    private String idCard;
    private String cardType;
}
