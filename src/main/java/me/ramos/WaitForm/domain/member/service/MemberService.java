package me.ramos.WaitForm.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ramos.WaitForm.domain.member.dto.MemberResponseDto;
import me.ramos.WaitForm.domain.member.exception.MemberNotFoundException;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import me.ramos.WaitForm.global.config.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto getMyInfo() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        log.info("회원 PK값: [{}]", currentMemberId);

        return memberRepository.findById(currentMemberId)
                .map(MemberResponseDto::of)
                .orElseThrow(MemberNotFoundException::new);
    }

    public MemberResponseDto findByMemberNickname(String nickname) {
        return memberRepository.findByNickname(nickname)
                .map(MemberResponseDto::of)
                .orElseThrow(MemberNotFoundException::new);
    }
}
