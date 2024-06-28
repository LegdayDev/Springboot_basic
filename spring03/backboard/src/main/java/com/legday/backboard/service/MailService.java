package com.legday.backboard.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;
    private final ResetService resetService;

    // 메일에서 초기화할 화면으로 이동 URL
    private String resetPassUrl = "http://localhost:8080/member/reset-password";

    @Value("${spring.mail.username}")
    private String from;

    // 랜덤 문자열을 반환하는 메서드 생성
    private String makeUuid() {
        return UUID.randomUUID().toString();
    }


    /**
     * <h3>패스워드 초기화 메일 전송 메서드</h3>
     * <li>UUID 를 사용한다.</li>
     * @param email
     * @return boolean
     */
    @Transactional
    public boolean sendResetPasswordEmail(String email) {
        String uuid = makeUuid();

        String subject = "요청하신 비밀번호 재설정입니다";
        String message = "BackBoard"
                + "<br><br>" + "아래의 링크를 클릭하면 비밀번호 재설정 페이지로 이동합니다." + "<br>"
                + "<a href='" + resetPassUrl + "/" + uuid + "'>"
                + resetPassUrl + "/" + uuid + "</a>" + "<br><br>";

        try {
            sendMail(email, subject, message);
            saveUuidAndEmail(uuid, email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    private void saveUuidAndEmail(String uuid, String email) {
        resetService.saveReset(uuid, email);
    }


    /**
     * <h3>메일 전송 메서드</h3>
     *
     * @param to
     * @param subject
     * @param message
     */
    public void sendMail(String to, String subject, String message) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage(); // MIME type 설정

        try {
            // MimeMessageHelper 로 MimeMessage 구성
            MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            // 이메일 수신자 설정
            mmh.setTo(to);
            // 이메일 제목 설정
            mmh.setSubject(subject);
            // 이메일 본문 내용 설정
            mmh.setText(message, true);
            // 이메일 발신자 설정
            mmh.setFrom(new InternetAddress(from));
            // 이메일 전송
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
