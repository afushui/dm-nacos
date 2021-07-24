package com.alibaba.dm.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserResponse implements Serializable {
    private Long userId;
    private String phone;
    private String wxUserID;
    private String realName;
    private String nickName;
    private Long sex;
    private String interest;
    private String idCard;
    private String birthday;
    private String createdTime;
    private String updatedTime;
    private String hobby;
    private Long imageId;
    private String imgUrl;
    private String token;//token:PC/MOBILE-md5()-userId-md5().substring(0,6)
    private Long extTime;//有效时长
    private Long genTime;//生成时间


}
