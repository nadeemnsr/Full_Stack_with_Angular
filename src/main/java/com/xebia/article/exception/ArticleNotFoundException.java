package com.xebia.article.exception;
public class ArticleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public ArticleNotFoundException(String id) {

        super(id);
    }
}