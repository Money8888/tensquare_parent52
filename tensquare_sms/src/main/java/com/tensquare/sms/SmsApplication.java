package com.tensquare.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.SmsUtil;

@SpringBootApplication
public class SmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class);
    }

    @Bean
    public SmsUtil smsUtil(){
        return new SmsUtil();
    }
}
