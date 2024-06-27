package com.legday.backboard.service;

import com.legday.backboard.common.NotFoundException;
import com.legday.backboard.entity.Board;
import com.legday.backboard.entity.Category;
import com.legday.backboard.entity.Member;
import com.legday.backboard.entity.Reply;
import com.legday.backboard.repository.BoardRepository;
import com.legday.backboard.validation.BoardForm;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAllBoard() {
        return boardRepository.findAll();
    }

    public Board findBoard(Long bno) {
        return boardRepository.findByBno(bno).orElseThrow(() -> {
            throw new NotFoundException("Board Not Found");
        });
    }

    // 페이징 되는 리스트 메서드
    public Page<Board> boardList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));   // pageSize 동적으로 변경 가능
        return this.boardRepository.findAll(pageable);
    }

    public Page<Board> boardList(int page, String keyword) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));   // pageSize 동적으로 변경 가능

        return boardRepository.findAllByKeyword(keyword, pageable);
    }

    public Page<Board> boardList(int page, String keyword, Category category) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));   // pageSize 동적으로 변경 가능

        Specification<Board> spec = searchBoard(keyword, category.getId());
        return boardRepository.findAll(spec, pageable);
    }

    @Transactional
    public Long saveBoard(String title, String content, Member writer) {
        // PK 없으면 INSERT
        return boardRepository.save(Board.builder().title(title).content(content).writer(writer).createDate(LocalDateTime.now()).build()).getBno();
    }

    @Transactional
    public void updateBoard(Board board, BoardForm boardForm) {
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        board.setModifyDate(LocalDateTime.now());

        // PK 있으면 UPDATE
        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Board board) {
        boardRepository.delete(board);
    }

    // 검색쿼리 대신 검색기능 생성
    public Specification<Board> searchBoard(String keyword) {
        return new Specification<Board>() {
            private static final long serialVersionUID = 1L;    // 필요한 값이라서 추가

            @Override
            public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // query를 JPA로 생성
                query.distinct(true);   // 중복제거
                Join<Board, Reply> r = b.join("replies", JoinType.LEFT);

                return cb.or(cb.like(b.get("title"), "%" + keyword + "%"),  // 게시글 제목에서 검색
                        cb.like(b.get("content"), "%" + keyword + "%"), // 게시글 내용에서 검색
                        cb.like(r.get("content"), "%" + keyword + "%"));    // 댓글 내용에서 검색
            }
        };
    }

    // 카테고리 추가 관련 메서드
    public Specification<Board> searchBoard(String keyword, Integer cateId) {
        return new Specification<Board>() {
            private static final long serialVersionUID = 1L;    // 필요한 값이라서 추가

            @Override
            public Predicate toPredicate(Root<Board> b, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // query를 JPA로 생성
                query.distinct(true);   // 중복제거
                Join<Board, Reply> r = b.join("replies", JoinType.LEFT);

                return cb.and(cb.equal(b.get("category").get("id"), cateId),
                        cb.or(cb.like(b.get("title"), "%" + keyword + "%"),  // 게시글 제목에서 검색
                                cb.like(b.get("content"), "%" + keyword + "%"), // 게시글 내용에서 검색
                                cb.like(r.get("content"), "%" + keyword + "%")
                        ));
            }
        };
    }

    @Transactional
    public void saveBoard(String title, String content, Member writer, Category findCategory) {
        boardRepository.save(Board.builder().title(title).content(content).writer(writer).category(findCategory).createDate(LocalDateTime.now()).build());
    }

    /**
     * <h3>조회수 증가 메서드</h3>
     * <li>게시물 ID값으로 조회수를 증가시킨다.</li>
     *
     * @param bno
     * @return Board
     */
    @Transactional
    public Board hitBoard(Long bno) {
        Board boardPS = boardRepository.findById(bno).orElseThrow(() -> {
            throw new NotFoundException("Board Not Found");
        });
        if (boardPS.getHit() == null) {
            boardPS.setHit(0);
            boardPS.setHit(boardPS.getHit() + 1);
        } else {
            boardPS.setHit(boardPS.getHit() + 1);
        }
        return boardPS;
    }
}
