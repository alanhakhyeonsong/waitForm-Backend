package me.ramos.WaitForm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.ramos.WaitForm.domain.member.dto.MemberLoginRequestDto;
import me.ramos.WaitForm.domain.member.dto.MemberRegisterRequestDto;
import me.ramos.WaitForm.domain.member.service.AuthService;
import me.ramos.WaitForm.global.config.jwt.dto.TokenDto;
import me.ramos.WaitForm.global.config.jwt.dto.TokenRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Mock
    private AuthService authService;

    @Test
    @DisplayName(value = "회원가입 테스트 - 성공")
    public void signUpTest() throws Exception {
        //given
        String email = "test@test.com";
        String nickname = "test";
        String password = "123456";

        //when
        String body = mapper.writeValueAsString(new MemberRegisterRequestDto(email, nickname, password));

        //then
        mvc.perform(post("/auth/signup")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName(value = "로그인 테스트 - 존재하지 않는 유저일 경우 실패")
    public void loginFailTest() throws Exception {
        //given
        String email = "test@test.com";
        String password = "123456";

        //when
        String body = mapper.writeValueAsString(new MemberLoginRequestDto(email, password));

        //then
        mvc.perform(post("/auth/login")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName(value = "로그인 테스트 - 성공")
    public void loginTest() throws Exception {
        //given
        String email = "test@test.com";
        String password = "123456";
        MemberLoginRequestDto request = new MemberLoginRequestDto(email, password);

        TokenDto tokenDto = TokenDto.builder()
                .grantType("bearer")
                .accessToken("aaa.bbb.ccc")
                .refreshToken("ccc.bbb.aaa")
                .accessTokenExpiresIn(1L)
                .build();

        //when
        when(authService.login(any())).thenReturn(tokenDto);

        //then
        TokenDto token = authService.login(request);
        assertEquals(token, tokenDto);
    }

    @Test
    @DisplayName(value = "토큰 재발급 테스트 - 성공")
    public void reissueTest() throws Exception {
        //given
        TokenDto tokenDto = TokenDto.builder()
                .grantType("bearer")
                .accessToken("aaa.bbb.ccc")
                .refreshToken("ccc.bbb.aaa")
                .accessTokenExpiresIn(1L)
                .build();

        //when
        when(authService.reissue(any())).thenReturn(tokenDto);

        //then
        TokenDto token = authService.reissue(new TokenRequestDto());
        assertEquals(token, tokenDto);
    }
}
