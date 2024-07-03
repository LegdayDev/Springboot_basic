package com.legday.backboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Header<T> {

    private LocalDateTime transactionTime; // JSON 으로 전달한 시간
    private String resultCode; // 트랜잭션이 성공인지 실패인지
    private String description;
    private T data; // 실제 게시글 데이터 담는 곳
    private PagingDto paging;

    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK() {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("NO ERROR")
                .build();
    }

    // Data 받아서 전달
    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(T data, PagingDto paging) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("NO ERROR")
                .data(data)
                .paging(paging)
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> Header<T> OK(String description) {
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }
}
