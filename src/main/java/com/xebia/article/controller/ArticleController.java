package com.xebia.article.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xebia.article.entity.ArticleEntity;
import com.xebia.article.logging.LogTimeFilter;
import com.xebia.article.model.ArticleRequestDto;
import com.xebia.article.model.ArticleResponseDto;
import com.xebia.article.model.ReadFileTiming;
import com.xebia.article.model.Tags;
import com.xebia.article.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "http://localhost:4211")
public class ArticleController {

	Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	ArticleService service;

	@Autowired
	ModelMapper medel;

	@Autowired
	LogTimeFilter logTimeFilter;

	@Autowired
	ObjectMapper mapper;

	@GetMapping(path = "/{userId}")
	public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable("userId") String userId) {
		ArticleEntity articleEntity = service.getArticleById(userId);
		medel.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ArticleResponseDto response = medel.map(articleEntity, ArticleResponseDto.class);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleRequestDto articleEntityRequest) {
		ArticleEntity articleEntity = service.createArticle(articleEntityRequest);
		medel.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ArticleResponseDto response = medel.map(articleEntity, ArticleResponseDto.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PatchMapping(path = "/{userId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable("userId") String userId,
			@RequestBody Map<Object, Object> articleEntityRequest)
			throws InvocationTargetException, IllegalAccessException {

		ArticleRequestDto toBePatchedManager = mapper.convertValue(articleEntityRequest, ArticleRequestDto.class);

		medel.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		ArticleEntity articleEntity = service.updateArticle(medel.map(toBePatchedManager, ArticleRequestDto.class),
				userId);
		ArticleResponseDto response = medel.map(articleEntity, ArticleResponseDto.class);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<ArticleResponseDto> deleteArticle(@PathVariable("userId") String userId) {
		service.deleteArticle(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public Page<ArticleEntity> getArticleList(@RequestParam(value = "page", defaultValue = "0") int pageNo,
			@RequestParam(value = "size", defaultValue = "1") int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		return service.getArticles(paging);
	}

	@GetMapping(path = "/tags")
	public ResponseEntity<List<Tags>> getTags() {
		List<Tags> articleEntity = service.tagsRecord();
		return ResponseEntity.status(HttpStatus.OK).body(articleEntity);
	}

	@GetMapping(path = "/{slug_uuid}/timetoread")
	public ResponseEntity<ReadFileTiming> readFile(@PathVariable("slug_uuid") String uuid) {
		ReadFileTiming readFileTiming = service.readFileTiming(uuid);
		return ResponseEntity.status(HttpStatus.OK).body(readFileTiming);
	}

	@GetMapping(path = "/hello-world")
	public String get() {
		return "Hello World ";
	}

}
