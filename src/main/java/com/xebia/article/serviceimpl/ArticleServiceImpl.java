package com.xebia.article.serviceimpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xebia.article.entity.ArticleEntity;
import com.xebia.article.exception.ArticleNotFoundException;
import com.xebia.article.exception.ArticleServiceException;
import com.xebia.article.exception.NoDataFoundException;
import com.xebia.article.logging.LogTimeFilter;
import com.xebia.article.model.ArticleRequestDto;
import com.xebia.article.model.ArticleResponseDto;
import com.xebia.article.model.ReadFileTiming;
import com.xebia.article.model.Tags;
import com.xebia.article.model.TimeOrReadDto;
import com.xebia.article.repository.ArticleRepository;
import com.xebia.article.service.ArticleService;
import com.xebia.article.util.NullAwareBeanUtilsBean;
import com.xebia.article.util.Utils;

@Service
public class ArticleServiceImpl implements ArticleService {

	Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
	ArticleRepository repository;
	Utils util;
	NullAwareBeanUtilsBean utilsBean;
	LogTimeFilter logTimeFilter;

	@Autowired
	public ArticleServiceImpl(ArticleRepository repository, Utils util, NullAwareBeanUtilsBean utilsBean,
			LogTimeFilter logTimeFilter) {
		this.repository = repository;
		this.util = util;
		this.utilsBean = utilsBean;
		this.logTimeFilter = logTimeFilter;
	}

	@Override
	public ArticleEntity createArticle(ArticleRequestDto request) {
		ArticleResponseDto response = new ArticleResponseDto();

		if (!repository.findByTitleAndDescription(request.getTitle(), request.getBody()).isEmpty()) {

			throw new ArticleServiceException("Record Already Exist Please Insert Different Value for Title and Body");
		}

		response.setTitle(request.getTitle());
		response.setBody(request.getBody());
		response.setDescription(request.getDescription());
		ArticleEntity dto = new ArticleEntity();
		dto.setId(util.generateUserId());
		dto.setBody(request.getBody());
		dto.setDescription(request.getDescription());
		dto.setTitle(request.getTitle());
		dto.setSlug("");
		dto.setTags(request.getTags());

		repository.save(dto);

		return dto;
	}

	@Override
	public ArticleEntity updateArticle(ArticleRequestDto request, String id) {

		Optional<ArticleEntity> byId = repository.findById(id);
		if (!byId.isPresent()) {
			throw new ArticleNotFoundException(id);
		}
		else {
			ArticleEntity articleEntity = byId.get();
			try {
				utilsBean.copyProperties(articleEntity, request);
			} catch (InvocationTargetException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
			repository.save(articleEntity);
			return articleEntity;
		}
	}

	@Override
	public ArticleEntity getArticleById(String id) {
		return repository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));

	}

	@Override
	public void deleteArticle(String id) {
		repository.deleteById(id);
	}

	@Override
	public Page<ArticleEntity> getArticles(Pageable pageabel) {
		Page<ArticleEntity> data = repository.findAll(pageabel);

		if (data.isEmpty()) {
			throw new NoDataFoundException();
		}
		return data;
	}

	@Override
	public List<Tags> tagsRecord() {
		Iterable<ArticleEntity> data = repository.findAll();
		if (((List<?>) data).isEmpty()) {
			throw new NoDataFoundException();
		}
		Map<String, Integer> map = new HashMap<>();

		List<Tags> list = new ArrayList<>();
		// add each element into the collection
		for (ArticleEntity t : data) {
			for (String str : t.getTags()) {
				String key = str.toLowerCase();
				if (map.containsKey(key)) {
					map.put(key, map.get(key) + 1);
				} else {
					map.put(key, 1);
				}
			}

		}
		map.forEach((k, v) -> {
			Tags tags = new Tags(k, v);
			list.add(tags);
		});
		return list;
	}

	@Override
	public ReadFileTiming readFileTiming(String uuid) {
		getArticleById(uuid);
		ReadFileTiming readFileTiming = new ReadFileTiming();
		readFileTiming.setArtcleId(uuid);
		long milliseconds = LogTimeFilter.duration;
		long minutes = (milliseconds / 1000) / 60;
		long seconds = (milliseconds / 1000) % 60;
		TimeOrReadDto timeToReadDto = new TimeOrReadDto(minutes, seconds);
		readFileTiming.setTimeOrReadDto(timeToReadDto);
		return readFileTiming;
	}
}
