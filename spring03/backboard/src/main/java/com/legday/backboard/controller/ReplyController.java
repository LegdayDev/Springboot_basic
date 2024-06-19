package com.legday.backboard.controller;

import com.legday.backboard.entity.Board;
import com.legday.backboard.service.BoardService;
import com.legday.backboard.service.ReplyService;
import com.legday.backboard.validation.ReplyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Slf4j
@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final BoardService boardService;

    @PostMapping("/create/{bno}")
    public String createReply(@Valid ReplyForm form, BindingResult bindingResult, @PathVariable("bno") Long bno, Model model) {
        Board board = boardService.findBoard(bno);
        if (bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board/detail";
        }
        replyService.replySave(board, form.getContent());
        log.info("Reply save success");
        model.addAttribute("board", board);
        return String.format("redirect:/board/detail/%s", bno);
    }
}
