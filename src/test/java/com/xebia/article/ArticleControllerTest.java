package com.xebia.article;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.xebia.article.controller.ArticleController;
import com.xebia.article.entity.ArticleEntity;
import com.xebia.article.model.ArticleRequestDto;
import com.xebia.article.model.ArticleResponseDto;
import com.xebia.article.service.ArticleService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ArticleController.class)
public class ArticleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ArticleService articleService;

	ArticleResponseDto mockCourse = new ArticleResponseDto("I am the best1", "I am the best and great22222222222",
			"I am so lucky", Arrays.asList("java", "spring Boot", "tutorial", "nadeem"));

	String exampleArticleJson = "{\r\n" + "\"title\":\"I am the best1\",\r\n"
			+ "\"description\":\"I am the best and great22222222222\",\r\n" + "\"body\":\"I am so lucky\",\r\n"
			+ "\"tags\":[\"java\",\"spring Boot\",\"tutorial\",\"nadeem\"]\r\n" + "\r\n" + "}";

	@Test
	public void createArticleTest() throws Exception {
		ArticleEntity articleEntity = new ArticleEntity("4e2297b1-be57-4dac-8beb-03ea56f4f574", "", "I am the best1",
				"I am the best and great", "I am so lucky", new Date("2020-06-29T11:51:55.211+0000"),
				new Date("2020-06-29T11:51:55.211+0000"), false, 0, Arrays.asList("java", "spring Boot", "tutorial", "nadeem"));

		Mockito.when(articleService.createArticle(Mockito.any(ArticleRequestDto.class))).thenReturn(articleEntity);

		// Send article as body to /api/articles
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/articles").accept(MediaType.APPLICATION_JSON)
				.content(exampleArticleJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		// assertEquals("http://localhost/students/Student1/courses/1",
		// response.getHeader(HttpHeaders.LOCATION));

	}
}
