package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import util.SmsUtil;


import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    /**
     * 读取application.yml的配置，保存成key value格式
     */
    @Autowired
    private Environment env;

    /**
     *读取配置参数的另一种方式,采取$符号读取配置
     */

    /*@Value("${aliyun.sms.template_code}")
    private String template_code;
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;*/


    @RabbitHandler
    public void consume(Map<String, String> message){
        String mobile = message.get("mobile");
        String checkcode = message.get("checkcode");
        String template_code = env.getProperty("aliyun.sms.template_code");
        String sign_name = env.getProperty("aliyun.sms.sign_name");
        try {
            smsUtil.sendSms(mobile, template_code, sign_name, "{\"checkcode\" : \""+ checkcode + "\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
