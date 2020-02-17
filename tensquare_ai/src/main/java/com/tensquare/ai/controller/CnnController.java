package com.tensquare.ai.controller;

import com.tensquare.ai.service.CnnService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cnn")
public class CnnController {

    @Autowired
    private CnnService cnnService;

    @RequestMapping(value = "/textclassify", method = RequestMethod.POST)
    public Result textClassify(@RequestBody Map<String, String> content){
        Map map = cnnService.textClassify(content.get("content"));
        return new Result(true, StatusCode.OK, "分类成功", map);
    }
}
