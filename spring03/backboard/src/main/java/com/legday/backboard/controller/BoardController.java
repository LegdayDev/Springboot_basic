package com.legday.backboard.controller;

import com.legday.backboard.config.auth.PrincipalDetails;
import com.legday.backboard.entity.Board;
import com.legday.backboard.service.BoardService;
import com.legday.backboard.validation.BoardForm;
import com.legday.backboard.validation.ReplyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;


    @GetMapping({"/board/list", "/"})
    public String list(Model model, @RequestParam(value ="page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String keyword) {
        Page<Board> paging = this.boardService.boardList(page, keyword);   // 검색추가
        model.addAttribute("paging", paging);
        model.addAttribute("kw", keyword);

        return "/board/list";
    }

    @GetMapping("/board/detail/{bno}")
    public String detailForm(@PathVariable("bno") Long bno, Model model, ReplyForm replyForm) {
        model.addAttribute("replyForm", replyForm);
        model.addAttribute("board", boardService.findBoard(bno));
        return "/board/detail";
    }

    @PreAuthorize(("isAuthenticated()"))
    @GetMapping("/board/create")
    public String createForm(BoardForm boardForm, Model model) {
        model.addAttribute("boardForm", boardForm);
        return "/board/create";
    }

    @PreAuthorize(("isAuthenticated()"))
    @PostMapping("/board/create")
    public String create(@Valid BoardForm form, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (bindingResult.hasErrors())
            return "redirect:/board/create";

        Long bno = boardService.saveBoard(form.getTitle(), form.getContent(), principalDetails.getMember());

        return "redirect:/board/detail/" + bno;
    }

    @PreAuthorize(("isAuthenticated()"))
    @GetMapping("/board/modify/{bno}")
    public String modifyForm(@PathVariable("bno") Long bno, BoardForm boardForm,
                             Model model,
                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Board board = boardService.findBoard(bno);
        if (!board.getWriter().getUsername().equals(principalDetails.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        model.addAttribute("boardForm", boardForm);

        return "/board/create";
    }

    @PreAuthorize(("isAuthenticated()"))
    @PostMapping("/board/modify/{bno}")
    public String modify(@PathVariable("bno") Long bno,
                         @Valid BoardForm boardForm,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (bindingResult.hasErrors())
            return "/board/create";

        Board board = boardService.findBoard(bno);

        if (!board.getWriter().getUsername().equals(principalDetails.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다!");

        boardService.updateBoard(board, boardForm);

        return "redirect:/board/detail/" + bno;
    }

    @PreAuthorize(("isAuthenticated()"))
    @GetMapping("/board/delete/{bno}")
    public String delete(@PathVariable("bno") Long bno,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Board board = boardService.findBoard(bno);
        if (!board.getWriter().getUsername().equals(principalDetails.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다!");

        boardService.deleteBoard(board);

        return "redirect:/board/list";
    }
}