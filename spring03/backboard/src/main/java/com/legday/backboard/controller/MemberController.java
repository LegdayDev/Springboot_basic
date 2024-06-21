package com.legday.backboard.controller;

import com.legday.backboard.service.MemberService;
import com.legday.backboard.validation.MemberForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(){
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
}
