package com.alibaba.dm.client.user;

import com.alibaba.dm.entity.user.LinkUser;
import com.alibaba.dm.entity.user.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "dm-user-provider")
public interface UserClient {

    //根据手机号返回用户对象
    @PostMapping(path = "/user/valid/{phone}")
    public User findUserByPhone(@PathVariable("phone") String phone);

    //新增用户
    @PostMapping(path = "/user/insert")
    public int addUser(@RequestBody User user);

    //修改用户
    @PostMapping("/user/upUser")
    public void upUser(@RequestBody User user);


    //根句用户ID返货用户信息
    @PostMapping("/user/findById")
    public User findById(@RequestParam Long userId);

    //根据用户Id返回所有的购票人
    @PostMapping("/user/queryLinkUser/{userId}")
    public List<LinkUser> queryLinkUser(@PathVariable("userId") Long userId);


    //新增购票人
    @PostMapping("/user/addLinkUser")
    public void addLinkUser(@RequestBody() LinkUser dmLinkUser);


    //验证购票人是否存在
    @PostMapping("/user/queryLinkUserByIdCard")
    public LinkUser queryLinkUserByIdCard(@RequestParam() String idCard);

    //根据ID删除购票人
    @PostMapping("/user/deleteticketbuyer/{linkId}")
    public int deleteticketbuyer(@PathVariable("linkId")Long linkId);

    //根据ID返回购票人信息
    @PostMapping("/user/queryLinkUserById/")
    public LinkUser queryLinkUserById(@RequestParam("parseLong") long parseLong);
}
