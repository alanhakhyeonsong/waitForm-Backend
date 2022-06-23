package me.ramos.WaitForm.service;

import me.ramos.WaitForm.domain.member.dto.MemberRegisterRequestDto;
import me.ramos.WaitForm.domain.member.entity.Member;
import me.ramos.WaitForm.domain.member.exception.MemberEmailAlreadyExistException;
import me.ramos.WaitForm.domain.member.exception.MemberNicknameAlreadyExistException;
import me.ramos.WaitForm.domain.member.repository.MemberRepository;
import me.ramos.WaitForm.domain.member.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static me.ramos.WaitForm.support.helper.MemberTestHelper.givenMember;
import static me.ramos.WaitForm.support.helper.MemberTestHelper.givenMemberRegisterRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    AuthService authService;

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        MemberRegisterRequestDto dto = givenMemberRegisterRequest();
        Member member = givenMember();
        given(memberRepository.save(any())).willReturn(member);

        //when
        authService.signup(dto);

        //then
        assertThat(member.getEmail()).isEqualTo(dto.getEmail());
        assertThat(member.getNickname()).isEqualTo(dto.getNickname());
    }

    @Test
    public void 회원가입_실패_중복된_이메일() throws Exception {
        //given
        MemberRegisterRequestDto dto = givenMemberRegisterRequest();

        //when
        when(memberRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        //then
        assertThatThrownBy(() -> authService.signup(dto))
                .isInstanceOf(MemberEmailAlreadyExistException.class);
    }

    @Test
    public void 회원가입_실패_중복된_닉네임() throws Exception {
        //given
        MemberRegisterRequestDto dto = givenMemberRegisterRequest();

        //when
        when(memberRepository.existsByNickname(dto.getNickname())).thenReturn(true);

        //then
        assertThatThrownBy(() -> authService.signup(dto))
                .isInstanceOf(MemberNicknameAlreadyExistException.class);
    }
}
