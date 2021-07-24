package com.alibaba.dm.controller;

import com.alibaba.dm.service.UserService;
import com.alibaba.dm.vo.CommonResponse;
import com.alibaba.dm.vo.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService  dmUserService;
    @PostMapping(path = "/msg/login")
    CommonResponse login(@RequestBody Map<String,String> map, HttpServletRequest request){
        return  dmUserService.login(map,request);
    }
    @PostMapping(path = "/msg")
    CommonResponse msg(@RequestBody Map<String,String> map){ return  dmUserService.msg(map);}
    @PostMapping("/login")
    CommonResponse pLong(@RequestBody Map<String,String> map, HttpServletRequest request){return dmUserService.pLogin(map,request);}
    @PostMapping("/register")
    public CommonResponse register(@RequestBody Map<String,Object> map){return dmUserService.addUser(map);}
    @PostMapping(path = "/code")
    CommonResponse code(@RequestBody Map<String,String> map){ return  dmUserService.msg(map);}

    @PostMapping("querypersoninfo")
    CommonResponse<UserResponse> querypersoninfo(HttpServletRequest request){
        return dmUserService.querypersoninfo(request);

    }
    @PostMapping("modifypersoninfo")
    CommonResponse modifypersoninfo(@RequestBody Map<String,Object>map, HttpServletRequest request) throws ParseException {
        return dmUserService.modifyPersonInfo(map,request);
    }
}
