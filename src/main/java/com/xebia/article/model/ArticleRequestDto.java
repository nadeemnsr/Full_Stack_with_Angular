package com.xebia.article.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleRequestDto {
	private String title;
	private String description;
	private String body;
	private List<String> tags;
}
