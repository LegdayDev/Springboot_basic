package com.legday.backboard.controller;

import com.legday.backboard.entity.Member;
import com.legday.backboard.service.MailService;
import com.legday.backboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mail")
@Controller
public class MailController {

    private final MemberService memberService;
    private final MailService mailService;

    @PostMapping("/reset-mail")
    public String resetMail(@RequestParam("email") String email, Model model) {
        log.info("email = {}", email);

        try {
            // 유효한 이메일인지 체크
            Member memberPS = memberService.findByEmail(email);

            // 메일 전송
            if (mailService.sendResetPasswordEmail(memberPS.getEmail())) {
                model.addAttribute("result", "초기화 메일 전송성공!");
                log.info("MailController = {}", "메일 전송 성공 !!");
            }else {
                model.addAttribute("result", "초기화 메일 전송실패! 관리자에게 문의해주세요.");
                log.warn("MailController = {}", "메일 전송 실패 !!");
            }

        } catch (Exception e) {
            model.addAttribute("result", "초기화 메일 전송실패! 사용자가 없습니다!");
            log.warn("MainController = {}", "메일 전송 실패 !");
        }
        return "redirect:/";
    }
}
