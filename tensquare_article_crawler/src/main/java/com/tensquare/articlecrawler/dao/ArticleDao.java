package com.tensquare.articlecrawler.dao;

import com.tensquare.articlecrawler.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{
    @Modifying
    @Query(value = "UPDATE tb_article SET state = 1 WHERE id = ?", nativeQuery = true)
    public void updateState(String id);

    @Modifying
    @Query(value = "UPDATE tb_article SET thumbup = IFNULL(thumbup +1,1) WHERE id = ?", nativeQuery = true)
    public void addThumbup(String id);
}
