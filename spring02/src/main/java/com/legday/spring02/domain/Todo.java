package com.legday.spring02.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Todo {
    private Integer tno;
    private String title;
    private LocalDateTime dueDate;
    private String writer;
    private Integer isDone;
}
