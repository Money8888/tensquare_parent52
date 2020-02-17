package com.tensquare.find.dao;


import com.tensquare.find.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleFindDao extends ElasticsearchRepository<Article, String > {
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
