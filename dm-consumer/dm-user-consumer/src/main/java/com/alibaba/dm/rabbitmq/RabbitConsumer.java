package com.alibaba.dm.rabbitmq;

import com.alibaba.dm.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@RabbitListener(queues = {"dmsms"})
@Component
public class RabbitConsumer {

    @Autowired
    private SmsUtil smsUtil;

    @RabbitHandler
    public void sendSms(Map<String, String> message){
        smsUtil.sendCode(message.get("phone"), message.get("code"));
    }

}
