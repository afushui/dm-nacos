package com.alibaba.dm.entity.user;

import com.alibaba.dm.entity.BaseEnitty.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "dm_user")
public class User extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String phone;
    private String password;
    private String wxUserId;
    private String realName;
    private String nickName;
    private Long sex;
    private String hobby;
    private String idCard;
    private LocalDateTime birthday;
}
