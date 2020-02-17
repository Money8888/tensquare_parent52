package com.tensquare.find.controller;

import com.tensquare.find.pojo.Article;
import com.tensquare.find.service.ArticleFindService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleFindController {
    @Autowired
    private ArticleFindService articleFindService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleFindService.save(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/{key}/{page}/{size}", method = RequestMethod.GET)
    public Result findByKey(@PathVariable String key, @PathVariable int page, @PathVariable int size){
        Page<Article> pageData =  articleFindService.findByKey(key, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pageData.getTotalElements(), pageData.getContent()));
    }
}
