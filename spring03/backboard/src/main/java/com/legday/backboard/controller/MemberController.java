package com.legday.backboard.controller;

import com.legday.backboard.entity.Member;
import com.legday.backboard.entity.Reset;
import com.legday.backboard.service.MemberService;
import com.legday.backboard.service.ResetService;
import com.legday.backboard.validation.MemberForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
@Controller
public class MemberController {
    private final MemberService memberService;
    private final ResetService resetService;

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @GetMapping("/register")
    public String registerForm(MemberForm memberForm, Model model) {
        model.addAttribute("memberForm", memberForm);
        return "member/register";
    }

    @PostMapping("/register")
    public String register(@Valid MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/register";
        }

        if (!memberForm.getPassword().equals(memberForm.getRePassword())) {
            bindingResult.rejectValue("rePassword", "passwordInCorrect", "패스워드가 일치하지 않습니다!");
            return "member/register";
        }

        try {
            memberService.saveMember(memberForm.getUsername(), memberForm.getEmail(), memberForm.getPassword());
        } catch (DataIntegrityViolationException e) { // 데이터 무결성 예외
            e.printStackTrace();
            bindingResult.reject("registerFailed", "이미 등록된 사용자입니다.");
            return "member/register";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("registerFailed", e.getMessage());
            return "member/register";
        }
        return "redirect:/";
    }

    @GetMapping("/reset")
    public String resetForm() {
        return "member/reset";
    }

    @GetMapping("/reset-password/{uuid}")
    public String resetPasswordForm(@PathVariable("uuid") String uuid, Model model) {
        Reset resetPS = resetService.findResetByUuid(uuid);
        Member memberPS = memberService.findByEmail(resetPS.getEmail());

        MemberForm memberForm = new MemberForm();
        memberForm.setUsername(memberPS.getUsername());
        memberForm.setEmail(memberPS.getEmail());

        model.addAttribute("memberForm", memberForm);
        log.info("MemberController = 확인된 이메일({})", resetPS.getEmail());
        return "member/newpassword";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute MemberForm memberForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/newpassword";
        }

        if (!memberForm.getPassword().equals(memberForm.getRePassword())) {
            bindingResult.rejectValue("rePassword", "passwordInCorrect", "패스워드가 일치하지 않습니다!");
            return "member/register";
        }
        Member memberPS = memberService.findMember(memberForm.getUsername());
        memberPS.setPassword(memberForm.getPassword());

        memberService.saveMember(memberPS);

        return "redirect:/member/login";
    }
}
