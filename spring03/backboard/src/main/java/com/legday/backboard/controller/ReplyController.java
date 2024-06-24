package com.legday.backboard.controller;

import com.legday.backboard.config.auth.PrincipalDetails;
import com.legday.backboard.entity.Board;
import com.legday.backboard.entity.Reply;
import com.legday.backboard.service.BoardService;
import com.legday.backboard.service.ReplyService;
import com.legday.backboard.validation.ReplyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;


@RequestMapping("/reply")
@RequiredArgsConstructor
@Slf4j
@Controller
public class ReplyController {
    private final ReplyService replyService;
    private final BoardService boardService;

    @PreAuthorize(("isAuthenticated()"))
    @PostMapping("/create/{bno}")
    public String createReply(@Valid ReplyForm form,
                              BindingResult bindingResult,
                              @PathVariable("bno") Long bno,
                              Model model,
                              @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Board board = boardService.findBoard(bno);
        if (bindingResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board/detail";
        }

        Reply reply = replyService.saveReply(board, form.getContent(), principalDetails.getMember());
        log.info("Reply save success");
        model.addAttribute("board", board);
        return String.format("redirect:/board/detail/%s#reply_%s", bno, reply.getRno());
    }

    @PreAuthorize(("isAuthenticated()"))
    @GetMapping("/modify/{rno}")
    public String modifyForm(@PathVariable("rno") Long rno,
                             ReplyForm replyForm,
                             Model model,
                             @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Reply reply = replyService.findReply(rno);

        if (!reply.getWriter().getUsername().equals(principalDetails.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다!");

        replyForm.setContent(reply.getContent());
        model.addAttribute("replyForm", replyForm);

        return "/reply/modify";
    }

    @PreAuthorize(("isAuthenticated()"))
    @PostMapping("/modify/{rno}")
    public String modify(@Valid ReplyForm replyForm,
                         BindingResult bindingResult,
                         @PathVariable("rno") Long rno,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (bindingResult.hasErrors())
            return "/reply/modify";

        Reply reply = replyService.findReply(rno);
        if (!reply.getWriter().getUsername().equals(principalDetails.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다!");

        replyService.updateReply(reply, replyForm);

        return String.format("redirect:/board/detail/%s#reply_%s", reply.getBoard().getBno(), reply.getRno());
    }

    @PreAuthorize(("isAuthenticated()"))
    @GetMapping("/delete/{rno}")
    public String delete(@PathVariable("rno") Long rno, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Reply reply = replyService.findReply(rno);

        if (!reply.getWriter().getUsername().equals(principalDetails.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다!");

        replyService.deleteReply(rno);

        return "redirect:/board/detail/" + reply.getBoard().getBno();
    }
}
