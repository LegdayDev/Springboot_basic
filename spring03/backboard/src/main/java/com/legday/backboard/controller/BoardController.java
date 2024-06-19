package com.legday.backboard.controller;

import com.legday.backboard.service.BoardService;
import com.legday.backboard.validation.BoardForm;
import com.legday.backboard.validation.ReplyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/board")
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String boardForm(Model model) {
        model.addAttribute("boards", boardService.findAllBoard());
        return "board/list";
    }

    @GetMapping("/detail/{bno}")
    public String detailForm(@PathVariable("bno") Long bno, Model model, ReplyForm replyForm) {
        model.addAttribute("replyForm", replyForm);
        model.addAttribute("board", boardService.findBoard(bno));
        return "board/detail";
    }

    @GetMapping("/create")
    public String createForm(BoardForm boardForm, Model model) {
        model.addAttribute("boardForm", boardForm);
        return "board/create";
    }

    @PostMapping("/create")
    public String create(@Valid BoardForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "/board/create";

        Long bno = boardService.saveBoard(form.getTitle(), form.getContent());

        return "redirect:/board/detail/" + bno;
    }
}