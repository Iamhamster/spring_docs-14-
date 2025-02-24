package com.example.springdocs.auth.service;

import com.example.springdocs.auth.dto.AuthLoginRequestDto;
import com.example.springdocs.auth.dto.AuthLoginResponseDto;
import com.example.springdocs.auth.dto.AuthSignupRequestDto;
import com.example.springdocs.member.entity.Member;
import com.example.springdocs.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;

    @Transactional
    public void signup(AuthSignupRequestDto dto){
        Member member = new Member(dto.getEmail());
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public AuthLoginResponseDto login(AuthLoginRequestDto dto){
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalStateException("그 멤버는 없습니다."));
        return new AuthLoginResponseDto(member.getId());
    }
}
