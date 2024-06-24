package com.legday.backboard.service;

import com.legday.backboard.common.NotFoundException;
import com.legday.backboard.entity.Board;
import com.legday.backboard.entity.Member;
import com.legday.backboard.repository.BoardRepository;
import com.legday.backboard.validation.BoardForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<Board> boardList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        return boardRepository.findAll(PageRequest.of(page, 10, Sort.by(sorts)));
    }

    @Transactional
    public Long saveBoard(String title, String content, Member writer) {
        // PK 없으면 INSERT
        return boardRepository.save(Board.builder().title(title).content(content).writer(writer).createDate(LocalDateTime.now()).build()).getBno();
    }

    @Transactional
    public void updateBoard(Board board, BoardForm boardForm){
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        board.setModifyDate(LocalDateTime.now());

        // PK 있으면 UPDATE
        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Board board){
        boardRepository.delete(board);
    }
}
