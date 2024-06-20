package com.legday.backboard.service;

import com.legday.backboard.entity.Member;
import com.legday.backboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(String username, String email, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Member savedMember = memberRepository.save(
                Member.builder().
                        username(username).
                        email(email).
                        password(bCryptPasswordEncoder.encode(password)).
                        createDate(LocalDateTime.now()).build());

        return savedMember;
    }
}
