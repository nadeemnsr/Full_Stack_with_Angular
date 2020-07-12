package com.xebia.article.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.xebia.article.model.ReadFileTiming;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xebia.article.entity.ArticleEntity;
import com.xebia.article.model.ArticleRequestDto;
import com.xebia.article.model.Tags;

public interface ArticleService {

    ArticleEntity createArticle(ArticleRequestDto request);

    ArticleEntity getArticleById(String id);

    ArticleEntity updateArticle(ArticleRequestDto request, String id) throws InvocationTargetException, IllegalAccessException;

    void deleteArticle(String valueOf);

    Page<ArticleEntity> getArticles(Pageable pageable);
    
    List<Tags> tagsRecord();

    ReadFileTiming readFileTiming(String slug_uuid);
}
