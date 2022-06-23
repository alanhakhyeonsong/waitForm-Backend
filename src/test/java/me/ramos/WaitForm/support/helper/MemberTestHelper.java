package me.ramos.WaitForm.support.helper;

import me.ramos.WaitForm.domain.member.dto.MemberLoginRequestDto;
import me.ramos.WaitForm.domain.member.dto.MemberRegisterRequestDto;
import me.ramos.WaitForm.domain.member.entity.Authority;
import me.ramos.WaitForm.domain.member.entity.Member;

public class MemberTestHelper {

    public static Member givenMember() {
        return Member.builder()
                .email("songs4805@naver.com")
                .password("test")
                .nickname("Ramos")
                .authority(Authority.ROLE_USER)
                .build();
    }

    public static MemberRegisterRequestDto givenMemberRegisterRequest() {
        return new MemberRegisterRequestDto("songs4805@naver.com", "test", "Ramos");
    }

    public static MemberLoginRequestDto givenMemberLoginRequest() {
        return new MemberLoginRequestDto("songs4805@naver.com", "test");
    }
}
