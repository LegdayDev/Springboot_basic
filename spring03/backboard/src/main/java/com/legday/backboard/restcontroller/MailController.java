package com.legday.backboard.restcontroller;

import com.legday.backboard.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/mail")
@RestController
public class MailController {
    private final MailService mailService;

    @PostMapping("/test-email")
    public ResponseEntity<String> testEmail() {
        String to = "chlwodud0327@naver.com";
        String subject = "이 편지는 영국에서 시작된..";
        String message = "구란데 ㅋㅋ";

        mailService.sendMail(to, subject, message);
        return ResponseEntity.ok().body("전송~");
    }
}
