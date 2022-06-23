package me.ramos.WaitForm.service;

import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import me.ramos.WaitForm.domain.member.service.MemberService;
import me.ramos.WaitForm.global.config.util.SecurityUtil;
import me.ramos.WaitForm.support.WithMockCustomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.ramos.WaitForm.support.helper.MemberTestHelper.givenMember;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    SecurityUtil securityUtil;

    @InjectMocks
    MemberService memberService;

    @WithMockCustomUser
    @Test
    public void 내_정보_조회_성공() throws Exception {
        //given
        Member member = givenMember();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        //when
        String nickname = memberService.getMyInfo().getNickname();

        //then
        assertEquals(member.getNickname(), nickname);
    }
}
