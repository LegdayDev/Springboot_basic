package com.legday.backboard.service;

import com.legday.backboard.common.NotFoundException;
import com.legday.backboard.entity.Member;
import com.legday.backboard.entity.MemberRole;
import com.legday.backboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Member saveMember(String username, String email, String password) {
        Member member = Member.builder().
                username(username).
                email(email).
                password(bCryptPasswordEncoder.encode(password)).
                role(MemberRole.USER).
                build();
        member.setCreateDate(LocalDateTime.now());


        return memberRepository.save(member);
    }

    public Member findMember(String username){
        return memberRepository.findByUsername(username).orElseThrow(() -> {
            throw new NotFoundException("객체를 찾을 수 없습니다 !");
        });
    }
}
