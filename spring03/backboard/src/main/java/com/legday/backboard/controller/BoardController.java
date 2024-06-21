package com.legday.backboard.controller;

import com.legday.backboard.config.auth.PrincipalDetails;
import com.legday.backboard.service.BoardService;
import com.legday.backboard.validation.BoardForm;
import com.legday.backboard.validation.ReplyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;


    @GetMapping({"/board/list", "/"})
    public String boardFormV2(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        model.addAttribute("paging", boardService.boardList(page));
        return "board/list";
    }

    @GetMapping("/board/detail/{bno}")
    public String detailForm(@PathVariable("bno") Long bno, Model model, ReplyForm replyForm) {
        model.addAttribute("replyForm", replyForm);
        model.addAttribute("board", boardService.findBoard(bno));
        return "board/detail";
    }

    @PreAuthorize(("isAuthenticated()"))
    @GetMapping("/board/create")
    public String createForm(BoardForm boardForm, Model model) {
        model.addAttribute("boardForm", boardForm);
        return "board/create";
    }

    @PreAuthorize(("isAuthenticated()"))
    @PostMapping("/board/create")
    public String create(@Valid BoardForm form, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (bindingResult.hasErrors())
            return "redirect:/board/create";

        Long bno = boardService.saveBoard(form.getTitle(), form.getContent(), principalDetails.getMember());

        return "redirect:/board/detail/" + bno;
    }
}