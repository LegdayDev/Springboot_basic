package com.legday.spring02.domain;

import lombok.*;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Todo {
    private Integer tno;
    private String title;
    private LocalDateTime dueDate;
    private String writer;
    private Integer isDone;
}
