package com.alibaba.dm.controller;

import com.alibaba.dm.service.LinkUserService;
import com.alibaba.dm.vo.CommonResponse;
import com.alibaba.dm.vo.LinkUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class LinkUserController {
    @Autowired
    LinkUserService dmLinkUserService;


    @PostMapping("ticketbuyerlist")
    CommonResponse<List<LinkUserResponse>> queryLinkUser(HttpServletRequest request){
        return dmLinkUserService.queryLinkUser(request);
    }


    @PostMapping("addticketbuyer")
    CommonResponse addLinkUser(@RequestBody Map<String,Object> map, HttpServletRequest request){
        return dmLinkUserService.addLinkUser(map,request);
    }

    @PostMapping("validatebuyerexist")
    CommonResponse queryLinkUserByIdCard(@RequestBody Map<String,Object> map){
        return dmLinkUserService.queryLinkUserByIdCard(map.get("idCard").toString());
    }


    @PostMapping("deleteticketbuyer")
    CommonResponse deleteLinkUserById(@RequestBody()Map<String,Long> map){
        return dmLinkUserService.deleteLinkUserById(map.get("linkId"));
    }



}
