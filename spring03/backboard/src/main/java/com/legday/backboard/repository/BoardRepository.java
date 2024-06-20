package com.legday.backboard.repository;

import com.legday.backboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
// 페이징
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // 쿼리 메서드
    Optional<Board> findByBno(Long bno);

    Optional<Board> findByTitle(String title);

    List<Board> findByTitleLike(String title);

    // 페이징 JPA 쿼리
    // select b1_0.bno,b1_0.content,b1_0.create_date,b1_0.title from board b1_0 offset 0 rows fetch first 10 rows only
    Page<Board> findAll(Pageable pageable);
}
