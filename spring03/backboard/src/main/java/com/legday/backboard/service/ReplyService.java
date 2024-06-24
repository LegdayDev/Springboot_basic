package com.legday.backboard.service;

import com.legday.backboard.common.NotFoundException;
import com.legday.backboard.entity.Board;
import com.legday.backboard.entity.Member;
import com.legday.backboard.entity.Reply;
import com.legday.backboard.repository.ReplyRepository;
import com.legday.backboard.validation.ReplyForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public void saveReply(Board board, String content, Member member) {
        Reply reply = Reply.builder().board(board).content(content).writer(member).createDate(LocalDateTime.now()).build();
        log.info("댓글 객체 생성, reply = {}", reply);

        replyRepository.save(reply);
        log.info("댓글 객체 저장 완료!");
    }

    public Reply findReply(Long rno) {
        return replyRepository.findById(rno).orElseThrow(() -> {
            throw new NotFoundException("객체가 없습니다!");
        });
    }

    @Transactional
    public void updateReply(Reply reply, ReplyForm replyForm){
        reply.setContent(replyForm.getContent());
        reply.setModifyDate(LocalDateTime.now());
        replyRepository.save(reply);
    }

    @Transactional
    public void deleteReply(Long rno) {
        replyRepository.deleteById(rno);
    }
}
