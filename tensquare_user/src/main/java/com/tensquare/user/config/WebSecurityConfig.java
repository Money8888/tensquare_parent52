package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        authorizeRequests是security全注解实现的开端，表示开始说明需要的权限
        权限说明分两部分：
            1、拦截的路径
            2、该路径需要的权限
        antMatchers表示拦截什么路径
        permitAll表示任何权限都可以访问，为放行所有
        anyRequest()任何的请求都必须authenticated认证后才能访问
        .and().csrf().disable() 固定写法，使得csrf拦截失效
         */
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
