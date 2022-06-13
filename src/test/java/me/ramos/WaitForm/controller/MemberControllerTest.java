package me.ramos.WaitForm.controller;

import me.ramos.WaitForm.domain.member.dto.MemberResponseDto;
import me.ramos.WaitForm.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @Test
    @DisplayName("내 정보 조회하기 - 성공")
    @WithMockUser
    public void getMyInfoTest() throws Exception {
        //given
        MemberResponseDto memberResponseDto = new MemberResponseDto(1L, "test@test.com", "test");

        //when
        when(memberService.getMyInfo()).thenReturn(memberResponseDto);

        //then
        MemberResponseDto result = memberService.getMyInfo();
        assertEquals(result, memberResponseDto);
    }

    @Test
    @DisplayName(value = "닉네임으로 회원 조회 - 성공")
    public void findByMemberNicknameTest() throws Exception {
        //given
        MemberResponseDto memberResponseDto = new MemberResponseDto(1L, "test@test.com", "test");

        //when
        when(memberService.findByMemberNickname("test")).thenReturn(memberResponseDto);

        //then
        MemberResponseDto result = memberService.findByMemberNickname("test");
        assertEquals(result, memberResponseDto);
    }
}
