package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 获得请求上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 获得request域
        HttpServletRequest request = requestContext.getRequest();

        // 若是内部OPTIONS请求，直接放行
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        // 若是管理员登录，直接放行
        if(request.getRequestURI().indexOf("login") > 0){
            return null;
        }
        // 获得请求头
        String header = request.getHeader("Authorization");

        if(header != null && !"".equals(header)){
            if(header.startsWith("Bearer ")){
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    if(claims != null){
                        String role = (String) claims.get("roles");
                        if("admin".equals(role)){
                            // 设置转发请求头
                            requestContext.addZuulRequestHeader("Authorization", header);
                            // 放行
                            return null;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    requestContext.setSendZuulResponse(false);
                }
            }
        }
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
