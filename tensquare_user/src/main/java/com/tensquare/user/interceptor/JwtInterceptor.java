package com.tensquare.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    // 在验证之前拦截

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // true表示拦截全部，默认放行全部
        // 拦截器只负责拦截把请求头中的令牌进行解析验证
        System.out.println("经过了拦截器");
        // 获得token请求头参数，Authorization为token约定键
        final String head = request.getHeader("Authorization");
        // Bearer+空格+token 为token格式
        // 获得token，7为Bearer 的位数，下标从0开始
        if(head != null && head.startsWith("Bearer ")){
            String token = head.substring(7);
            try {
                Claims claims = jwtUtil.parseJWT(token);
                if(claims != null){
                    if("admin".equals(claims.get("roles"))){
                        request.setAttribute("claims_admin", token);
                    }
                    if("user".equals(claims.get("roles"))){
                        request.setAttribute("claims_user", token);
                    }
                }
            }catch (Exception e){
                throw new RuntimeException("令牌有误");
            }
        }
        return true;
    }
}
