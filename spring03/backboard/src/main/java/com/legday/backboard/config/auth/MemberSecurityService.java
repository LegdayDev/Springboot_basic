package com.legday.backboard.config.auth;

import com.legday.backboard.entity.Member;
import com.legday.backboard.entity.MemberRole;
import com.legday.backboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException(username);
        });

        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(username))
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        else
            authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));

        return new PrincipalDetails(member, authorities);
    }
}
