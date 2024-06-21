package com.legday.backboard.service;

import com.legday.backboard.entity.Board;
import com.legday.backboard.entity.Member;
import com.legday.backboard.entity.Reply;
import com.legday.backboard.repository.BoardRepository;
import com.legday.backboard.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public void replySave(Board board, String content, Member member){
        Reply reply = Reply.builder().board(board).content(content).writer(member).createDate(LocalDateTime.now()).build();
        log.info("댓글 객체 생성, reply = {}", reply);

        replyRepository.save(reply);
        log.info("댓글 객체 저장 완료!");
    }

}
