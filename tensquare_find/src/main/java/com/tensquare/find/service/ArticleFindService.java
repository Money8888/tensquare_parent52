package com.tensquare.find.service;

import com.tensquare.find.dao.ArticleFindDao;
import com.tensquare.find.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class ArticleFindService {
    @Autowired
    private ArticleFindDao articleFindDao;
    @Autowired
    private IdWorker idWorker;

    public void save(Article article){
        article.setId(idWorker.nextId()+"");
        articleFindDao.save(article);
    }

    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return articleFindDao.findByTitleOrContentLike(key, key, pageable);
    }
}
