package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value="/comment/{parentId}/{page}/{size}",method=RequestMethod.GET)
    public Result findByParentId(@PathVariable String parentId, @PathVariable int page, @PathVariable int size){
        Page<Spit> pageList = spitService.findByParentid(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageList.getTotalElements(), pageList.getContent()));
    }

    @RequestMapping(value = "/thumbup/{id}", method = RequestMethod.PUT)
    public Result updateThumbup(@PathVariable String id){
        String userid = "123";
        if(redisTemplate.opsForValue().get("thumbup"+ userid +"_" + id)!=null){
            return new Result(false, StatusCode.REPERROR, "重复点赞！");
        }else{
            spitService.thumbup(id);
            redisTemplate.opsForValue().set("thumbup"+ userid +"_" + id, "1");
            return new Result(true, StatusCode.OK, "点赞成功");
        }
    }
}
