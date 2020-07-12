package com.xebia.article;

import com.xebia.article.util.NullAwareBeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class XebiaAssignmentTestApplication {


    public static void main(String[] args) {
        SpringApplication.run(XebiaAssignmentTestApplication.class, args);
    }


    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    @Bean
    public RestTemplate getTemplate() {
        return new RestTemplate();
    }

    @Bean
    public NullAwareBeanUtilsBean nullAwareBeanUtilsBean() {
        return new NullAwareBeanUtilsBean();
    }

}
