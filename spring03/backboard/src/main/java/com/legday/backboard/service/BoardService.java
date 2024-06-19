package com.legday.backboard.service;

import com.legday.backboard.entity.Board;
import com.legday.backboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        return boardRepository.findById(bno).orElseThrow(() -> {
            throw new RuntimeException("Board Not Found");
        });
    }
    
    @Transactional
    public Long saveBoard(String title, String content){
        Board save = boardRepository.save(Board.builder().title(title).content(content).createDate(LocalDateTime.now()).build());
        return save.getBno();
    }
}
