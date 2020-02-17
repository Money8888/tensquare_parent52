package com.tensquare.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "danei")
public class Consumer3 {
    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("danei" + msg);
    }
}
