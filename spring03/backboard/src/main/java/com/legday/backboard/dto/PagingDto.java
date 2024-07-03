package com.legday.backboard.dto;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingDto {

    private int pageSize;       // 화면당 보여지는 게시글 최대개수
    private int totalPageNum;   // 총 페이지 수
    private long totalListSize; // 총 게시글 수

    private int page;           // 현재 페이지
    private int startPage;      // 시작 페이지 번호
    private int endPage;        // 마지막 페이지 번호

    private int startIndex;     // 시작 인덱스 번호

    private int block;          // 현재 블럭(구간)
    private int totalBlockNum;  // 총 블럭 수
    private int prevBlock;      // 이전 블럭
    private int nextBlock;      // 다음 블럭

    public PagingDto(Long totallistSize, Integer page, Integer pageSize, Integer blockSize) {
        this.pageSize = pageSize;
        this.page = page;
        this.totalListSize = totallistSize;

        // 변수값 계산(전체 블럭수 계산)
        this.totalPageNum = (int) Math.ceil(this.totalListSize * 1.0 / this.pageSize);

        // 현재 블럭 계산
        this.block = (int) Math.ceil((this.page) * 1.0 / blockSize);

        // 한 블럭 시작 페이지
        this.startPage = ((this.block - 1) * blockSize + 1);

        // 현재블럭 마지막페이지
        this.endPage = this.startPage + blockSize - 1;

        // 블럭 마지막페이지 검증(한 블럭이 10페이지가 안넘으면 마지막 페이지를 최대 페이지수로 다시 변경 10 -> 3)
        if (this.endPage > this.totalPageNum) this.endPage = this.totalPageNum;

        // 이전 블럭(클릭 시, 이전 블럭 마지막페이지)
        this.prevBlock = (this.block * blockSize) - blockSize;

        // 이전 블럭 검증
        if (this.prevBlock < 1) this.prevBlock = 1;

        // 다음 블럭
        this.nextBlock = (this.block * blockSize + 1);

        // 다음 블러 검증
        if(this.nextBlock > this.totalPageNum) this.nextBlock = this.totalPageNum;

        // 시작 인덱스 번호
        this.startIndex = (this.block - 1) * this.pageSize;
    }
}
