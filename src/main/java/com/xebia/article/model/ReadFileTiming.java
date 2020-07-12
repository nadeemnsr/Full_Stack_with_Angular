package com.xebia.article.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ReadFileTiming {

    private String artcleId;
    private TimeOrReadDto timeOrReadDto;

}

