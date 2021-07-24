package com.alibaba.dm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCommentResponse {
    private Long id;
    private Long itemId;
    private Long userId;
    private String imgUrl;
    private Long score;
    private String content;
    private String createdTime;
}
