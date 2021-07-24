package com.alibaba.dm.service;

import com.alibaba.dm.client.user.UserClient;
import com.alibaba.dm.entity.user.LinkUser;
import com.alibaba.dm.vo.CommonResponse;
import com.alibaba.dm.vo.LinkUserResponse;
import com.alibaba.dm.vo.VoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LinkUserService {
  @Autowired
  UserClient dmUserClient;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  //获取常用购票人
  public CommonResponse<List<LinkUserResponse>> queryLinkUser(HttpServletRequest request){

    String[] tokens = request.getHeader("token").split("-");
    String token = tokens[1];
    System.out.println(token+"=====================");
    List<LinkUserResponse> dmLinkUserResponses = new ArrayList<>();
    List<LinkUser> dmLinkUsers = dmUserClient.queryLinkUser(Long.parseLong(token));
    dmLinkUsers.forEach(dmLinkUser -> {
      LinkUserResponse dmLinkUserResponse = new LinkUserResponse();
      dmLinkUserResponse.setLinkId(dmLinkUser.getId());
      dmLinkUserResponse.setName(dmLinkUser.getName());
      dmLinkUserResponse.setIdCard(dmLinkUser.getIdCard());
      dmLinkUserResponse.setCardType(dmLinkUser.getCardType().toString());
      dmLinkUserResponses.add(dmLinkUserResponse);
    });
    return VoUtil.returnSuccess("0000",dmLinkUserResponses);
  }

  //添加常用购票人
  public CommonResponse addLinkUser(Map<String,Object> map, HttpServletRequest request){
    String[] tokens = request.getHeader("token").split("-");
    String token = tokens[1];
    LinkUser dmLinkUser = new LinkUser();
    dmLinkUser.setUserId(Long.parseLong(token));
    dmLinkUser.setName(map.get("name").toString());
    dmLinkUser.setIdCard(map.get("idCard").toString());
    dmLinkUser.setCardType(Long.parseLong(map.get("cardType").toString()));
    dmUserClient.addLinkUser(dmLinkUser);
    return VoUtil.returnSuccess(null,null);
  }

  //验证购票人是否已经存在
  public CommonResponse queryLinkUserByIdCard(String idCard) {
    LinkUser dmLinkUser = dmUserClient.queryLinkUserByIdCard(idCard);
    if (dmLinkUser==null){
      return VoUtil.returnSuccess("0000","购票人不存在");
    }else {
      return VoUtil.returnFailure("1009","购票人已存在");
    }
  }
  //删除联系人

  public CommonResponse deleteLinkUserById(Long linkId){

    try{
      dmUserClient.deleteticketbuyer(linkId);
      return VoUtil.returnSuccess("0000",null);

    }catch (Exception e){
      return VoUtil.returnFailure("10001",e.getMessage());
    }
  }

}
