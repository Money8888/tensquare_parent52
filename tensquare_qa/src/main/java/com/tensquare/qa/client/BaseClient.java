package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.ClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 调用基础模块的按照id查标签
 */
@FeignClient(value = "tensquare-base", fallback = ClientImpl.class)
public interface BaseClient {
    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId);
}
