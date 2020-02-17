package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    /**
     * 表示在请求前pre或请求后post执行的过滤
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的优先级，值越小优先级越大
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否开启过滤器
     * true表示开启，false表示不开启
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 表示过滤的逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        // 获取请求的上下文
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 得到request域
        HttpServletRequest request = requestContext.getRequest();
        // 获得头信息
        String header = request.getHeader("Authorization");
        if(header != null && !"".equals(header)){
            requestContext.addZuulRequestHeader("Authorization", header);
        }
        System.out.println("经过过滤器");
        return null;
    }
}
