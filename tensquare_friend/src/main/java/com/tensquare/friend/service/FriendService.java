package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {
        // 判断是否重复添加
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if(friend != null){
            return 0;
        }
        // 添加好友,islike 0表示单向喜欢，1表示双向喜欢
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        // 判断是否双向喜欢
        if(friendDao.findByUseridAndFriendid(friendid, userid) != null){
            friendDao.updateIsLike("1", userid, friendid);
            friendDao.updateIsLike("1", friendid, userid);
        }
        return 1;
    }

    public int addNoFriend(String userid, String friendid) {
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if(noFriend != null){
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        // 删除userid到friendid的好友列表数据
        friendDao.deleteFriend(userid, friendid);
        // 将好友的islike置0
        friendDao.updateIsLike("0", friendid, userid);
        // 保存成非好友的关系
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
