package com.xebia.article.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
class MyAspect {
    Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Before("execution(* com.xebia.article.serviceimpl.ArticleServiceImpl.*(..))")
    public void before(JoinPoint joinPoint) {
        logger.info("before! ");
        logger.info(joinPoint.getSignature().getName());
    }

    @Before("execution(* com.xebia.article.serviceimpl.ArticleServiceImpl.*(..))")
    public void after(JoinPoint joinPoint) {
        logger.info("After! ");
        logger.info(joinPoint.getSignature().getName());
    }


}