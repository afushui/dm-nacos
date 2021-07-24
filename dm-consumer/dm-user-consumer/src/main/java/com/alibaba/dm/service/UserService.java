package com.alibaba.dm.service;

import com.alibaba.dm.MD5;
import com.alibaba.dm.UserAgentUtil;
import com.alibaba.dm.client.base.ImageClient;
import com.alibaba.dm.client.user.UserClient;
import com.alibaba.dm.entity.base.Image;
import com.alibaba.dm.entity.user.User;
import com.alibaba.dm.vo.CommonResponse;
import com.alibaba.dm.vo.UserResponse;
import com.alibaba.dm.vo.VoUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.mallat.uasparser.UserAgentInfo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    UserClient dmUserClient;
    @Autowired
    ImageClient dmImageClient;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 手机验证码登陆
     * @param map
     * @param request
     * @return
     */
    public CommonResponse login(Map<String, String> map, HttpServletRequest request) {
        //手机号
        String phone = map.get("phone");
        //验证码
        String vcode = map.get("vcode");
        //token
        StringBuffer tokenBuffer = new StringBuffer("token:PC-");

        if (phone.length() != 11) {
            return VoUtil.returnFailure("1001", "手机号格式不正确");
        }
        //从redis中获取验证码
        String code = redisTemplate.opsForValue().get("sms_" + map.get("phone"));
        //比较
        UserResponse dmUserResponse = new UserResponse();
        if (code != null && code.equals(vcode)) {//验证码成功!
            User dmUser = dmUserClient.findUserByPhone(phone);
            if (dmUser == null) {
                //自动注册
                User user = new User();
                user.setPhone(phone);
                user.setNickName("小手冰凉");
                user.setRealName("小手冰凉");
                dmUserClient.addUser(user);
            }
            Date date = new Date();
            BeanUtils.copyProperties(dmUser, dmUserResponse);
            dmUserResponse.setUserId(dmUser.getId());
            //生成Token：PC-MD5(userCode,32)-userId-generateTime-user-agent
            tokenBuffer.append(MD5.getMd5(dmUser.getPhone(),32));
            tokenBuffer.append("-"+dmUser.getId());
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String s = format.format(date);
            tokenBuffer.append("-"+s);
            String userAgent = request.getHeader("User-Agent");
            try {
                UserAgentInfo userAgentInfo = UserAgentUtil.getUasParser().parse(userAgent);
                tokenBuffer.append("-"+MD5.getMd5(userAgentInfo.getDeviceType(),12));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dmUserResponse.setToken(tokenBuffer.toString());
            dmUserResponse.setGenTime(date.getTime());
            dmUserResponse.setExtTime(dmUserResponse.getGenTime() + 7200000L);
            try {
                redisTemplate.opsForValue().set(tokenBuffer.toString(),new ObjectMapper().writeValueAsString(dmUserResponse),30, TimeUnit.MINUTES);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return VoUtil.returnSuccess("登陆成功",dmUserResponse);
    }


    /**
     * 密码登陆
     * @param map
     * @param request
     * @return
     */
    public CommonResponse pLogin(Map<String,String > map,HttpServletRequest request){
        String phone =map.get("phone");
        String password=map.get("password");
        User dmUser = dmUserClient.findUserByPhone(phone);

        if (dmUser==null){
            return VoUtil.returnFailure("1006","手机号不存在");
        }else {
            if (!dmUser.getPassword().equals(MD5.getMd5(password,32))){
                return VoUtil.returnFailure("1006","用户密码错误");
            }
        }
        UserResponse dmUserResponse = new UserResponse();
        BeanUtils.copyProperties(dmUser,dmUserResponse);
        Image dmImage = dmImageClient.loginImage(dmUser.getId());
        if (dmImage!=null){
            dmUserResponse.setImageId(dmImage.getId());
            dmUserResponse.setImgUrl(dmImage.getImgUrl());
        }

//        //jwt
//        Map<String, Object> userInfo = new HashMap<>();
//        userInfo.put("name",dmUser.getNickName());
//        userInfo.put("phone",dmUser.getPhone());
//        userInfo.put("userId",dmUser.getId());
//        userInfo.put("password",dmUser.getPassword());
//        JwtBuilder builder = Jwts.builder();
//        builder.setId(UUID.randomUUID().toString());
//        builder.setSubject("{'username':'admin','pwd':'123'}");
//        builder.addClaims(userInfo);
//        builder.setIssuedAt(new Date());                                     //设置签发日期
//        builder.setExpiration(new Date(System.currentTimeMillis() + 5000));  //设置有效时间
//        builder.signWith(SignatureAlgorithm.HS256, "admin");


        dmUserResponse.setUserId(dmUser.getId());
        dmUserResponse.setGenTime(new Date().getTime());
        dmUserResponse.setExtTime(dmUserResponse.getGenTime() + 7200000L);
        StringBuffer token =new StringBuffer("user:PC_");
        token.append(MD5.getMd5(dmUser.getPhone(),32));
        token.append("-"+dmUser.getId());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String s = format.format(date);
        token.append("-"+s);
        String userAgent = request.getHeader("User-Agent");
        UserAgentInfo userAgentInfo = null;
        try {
            userAgentInfo = UserAgentUtil.getUasParser().parse(userAgent);
            token.append("-"+MD5.getMd5(userAgentInfo.getDeviceType(),12));
            dmUserResponse.setToken(token.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            redisTemplate.opsForValue().set(token.toString(),new ObjectMapper().writeValueAsString(dmUserResponse),30,TimeUnit.MINUTES);
        }catch (Exception e){
            e.printStackTrace();
        }
        return VoUtil.returnSuccess("登陆成功",dmUserResponse);
    }

    /**
     * 生成验证码
     * @param map
     * @return
     */
    public CommonResponse msg(Map<String,String> map){
        String c = redisTemplate.opsForValue().get("sms_"+map.get("phone"));
        if(c != null){
            return VoUtil.returnFailure("1002","同一手机号60秒内只能发送一次验证码");
        }
        //生成验证码
        int min = 100000;
        int max = 999999;
        Random random = new Random();
        int code = random.nextInt(max);
        if(code < min){
            code = code + min;
        }
        //存储验证码 Redis
        redisTemplate.opsForValue().set("sms_"+map.get("phone"),code+"",1L, TimeUnit.MINUTES);
        //短信通知(异步) Rabbitmq
        Map<String,String> message = new HashMap<>();
        message.put("phone",map.get("phone"));
        message.put("code",code+"");
        rabbitTemplate.convertAndSend("dmsms",message);
        return VoUtil.returnSuccess("短信发送成功",null);
    }

    public String token(){

        return  redisTemplate.opsForValue().get("");
    }



    public CommonResponse addUser(Map<String, Object> map){
        String code = redisTemplate.opsForValue().get("sms_" + map.get("phone"));
        if(map.get("vcode").toString().equals(code)){
            User dmUser = dmUserClient.findUserByPhone(map.get("phone").toString());
            if (dmUser !=null){
                return VoUtil.returnFailure("1002","手机号已存在");
            }
            User dmUser1 =new User();
            dmUser1.setPhone(map.get("phone").toString());
            dmUser1.setNickName("HHHUAJAJHH");
            dmUser1.setRealName("JJKKAKSLLASLD");
            dmUser1.setPassword(MD5.getMd5(map.get("password").toString(),32));
            dmUserClient.addUser(dmUser1);

            return VoUtil.returnSuccess(null,null);
        }else {
            return VoUtil.returnFailure("1001","验证码不正确");
        }

    }




    //修改用户信息
    public CommonResponse modifyPersonInfo(Map<String, Object> map ,HttpServletRequest request) throws ParseException {
        String[] tokens = request.getHeader("token").split("-");
        String token = tokens[1];
        User user = new User();
        System.out.println(token);
        user.setId(Long.parseLong(token));
        user.setNickName(map.get("nickName").toString());
        user.setPhone(map.get("phone").toString());
        user.setRealName(map.get("realName").toString());
        user.setSex(Long.parseLong(map.get("sex").toString()));
        user.setIdCard(map.get("idCard").toString());
        user.setHobby(map.get("interest").toString());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(map.get("birthday").toString());
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime birthday = instant.atZone(zoneId).toLocalDateTime();
        user.setBirthday(birthday);
        dmUserClient.upUser(user);
        return VoUtil.returnSuccess("0000",null);
    }


    // 根据用户TOKEN返回用户信息
    public CommonResponse<UserResponse> querypersoninfo(HttpServletRequest request){
        String userToken = redisTemplate.opsForValue().get(request.getHeader("token"));
        UserResponse dmUserResponse1 = JSON.parseObject(userToken, UserResponse.class);
        String[] tokens = request.getHeader("token").split("-");
        String token = tokens[1];
        User byId = dmUserClient.findById(Long.parseLong(token));

        UserResponse dmUserResponse = new UserResponse();
        BeanUtils.copyProperties(byId,dmUserResponse);
        if (byId.getBirthday()!= null){
            dmUserResponse.setBirthday(byId.getBirthday().toString());
        }
        //dmUserResponse.setBirthday(byId.getBirthday().toString());
        dmUserResponse.setSex(byId.getSex());
        return VoUtil.returnSuccess("0000",dmUserResponse);
    }




}
