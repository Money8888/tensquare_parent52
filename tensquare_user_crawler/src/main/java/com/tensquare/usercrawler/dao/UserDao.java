package com.tensquare.usercrawler.dao;

import com.tensquare.usercrawler.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
	public User findUserByMobile(String mobile);

	@Modifying
    @Query(value = "update tb_user set fanscount = fanscount + ? where id = ?", nativeQuery = true)
    public void updateFanscount(int x, String friendid);

	@Modifying
    @Query(value = "update tb_user set followcount = followcount + ? where id = ?", nativeQuery = true)
    public void updateFollowcount(int x, String userid);
}
