package com.example.springdocs.member.controller;

import com.example.springdocs.common.consts.Const;
import com.example.springdocs.member.dto.MemberResponseDto;
import com.example.springdocs.member.dto.MemberUpdateRequestDto;
import com.example.springdocs.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getAll(){
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponseDto> getOne(@PathVariable Long memberId){
        return ResponseEntity.ok(memberService.findById(memberId));
    }

    @PutMapping("/members")
    public void update(
        @SessionAttribute(name = Const.LOGIN_USER) Long memberId,
        @RequestBody MemberUpdateRequestDto dto
    ){
        memberService.update(memberId, dto);
    }

    @DeleteMapping("/members/{memberId}")
    public void delete(@SessionAttribute(name = Const.LOGIN_USER) Long memberId){
        memberService.deleteById(memberId);
    }
}
