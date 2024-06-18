package com.legday.backboard.service;

import com.legday.backboard.entity.Board;
import com.legday.backboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> boardList() {
        return boardRepository.findAll();
    }

    public Board findBoard(Long bno) {
        return boardRepository.findById(bno).orElseThrow(() -> {
            throw new RuntimeException("Board Not Found");
        });
    }
}
