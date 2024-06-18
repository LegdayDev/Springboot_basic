package com.legday.backboard.controller;

import com.legday.backboard.entity.Board;
import com.legday.backboard.service.BoardService;
import com.legday.backboard.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Log4j2
@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final BoardService boardService;

    @PostMapping("/create/{bno}")
    public String createReply(@PathVariable("bno") Long bno,
                              @RequestParam("content") String content,
                              Model model) {
        Board board = boardService.findBoard(bno);
        replyService.replySave(board, content);
        log.info("Reply save success");
        model.addAttribute("board",board);
        return String.format("redirect:/board/detail/%s", bno);
    }
}
