package com.xebia.article.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TimeOrReadDto {
    private long min;
    private long sec;
}

