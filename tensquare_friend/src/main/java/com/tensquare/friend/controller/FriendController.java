package com.tensquare.friend.controller;

import com.tensquare.friend.client.FriendClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private FriendClient friendClient;

    //删除好友
    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims == null || "".equals(claims)){
            return new Result(false, StatusCode.ERROR, "权限不足");
        }
        String userid = claims.getId();
        friendService.deleteFriend(userid, friendid);
        friendClient.updatefanscountandfollowcount(userid, friendid, -1);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    // type 表示喜欢程度，1表示喜欢，2表示不喜欢

    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type){
        //验证是否登录，并获取当前登录用户的id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if(claims == null || "".equals(claims)){
            return new Result(false, StatusCode.ERROR, "权限不足");
        }
        String userid = claims.getId();

        //判断添加好友还是非好友
        if(type != null){
            if("1".equals(type)){
                // 添加好友
                int flag = friendService.addFriend(userid, friendid);
                if(flag == 1){
                    friendClient.updatefanscountandfollowcount(userid, friendid, 1);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
                if(flag == 0){
                    return new Result(false, StatusCode.ERROR, "不能重复添加");
                }
            }else if("2".equals(type)){
                // 添加非好友
                int flag = friendService.addNoFriend(userid, friendid);
                if(flag == 1){
                    return new Result(true, StatusCode.OK, "添加成功");
                }
                if(flag == 0){
                    return new Result(false, StatusCode.ERROR, "不能重复添加");
                }
            }
            return new Result(false, StatusCode.ERROR, "参数错误");
        }
        return new Result(false, StatusCode.ERROR, "参数错误");
    }
}
