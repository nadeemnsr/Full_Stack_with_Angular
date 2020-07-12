package com.xebia.article.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xebia.article.entity.ArticleEntity;

@Repository
public interface ArticleRepository
		extends CrudRepository<ArticleEntity, String>, PagingAndSortingRepository<ArticleEntity, String> {
	Page<ArticleEntity> findAll(Pageable pageable);

	@Query("SELECT e from ArticleEntity e where e.title =:title or e.body =:body")
	List<ArticleEntity> findByTitleAndDescription(@Param("title") String title, @Param("body") String body);

}
