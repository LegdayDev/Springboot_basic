package com.legday.backboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ReplyDto {

    private Long rno; // PK

    private String content; // 글내용

    private LocalDateTime createDate; // 글생성일

    private LocalDateTime modifyDate;

    private String writer;
}
