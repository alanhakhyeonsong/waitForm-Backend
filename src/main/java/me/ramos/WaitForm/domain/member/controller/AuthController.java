package me.ramos.WaitForm.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.member.dto.MemberLoginRequestDto;
import me.ramos.WaitForm.domain.member.dto.MemberLogoutRequestDto;
import me.ramos.WaitForm.domain.member.dto.MemberRegisterRequestDto;
import me.ramos.WaitForm.domain.member.dto.MemberResponseDto;
import me.ramos.WaitForm.domain.member.service.AuthService;
import me.ramos.WaitForm.global.config.jwt.dto.TokenDto;
import me.ramos.WaitForm.global.config.jwt.dto.TokenRequestDto;
import me.ramos.WaitForm.global.result.ResultCode;
import me.ramos.WaitForm.global.result.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "이메일, 패스워드, 닉네임을 입력하여 회원가입을 합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력"),
            @ApiResponse(responseCode = "400", description = "이미 가입된 회원")
    })
    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signup(@Valid @RequestBody MemberRegisterRequestDto memberRegisterRequestDto) throws Exception {
        MemberResponseDto memberResponseDto = authService.signup(memberRegisterRequestDto);
        ResultResponse result = ResultResponse.of(ResultCode.REGISTER_SUCCESS, memberResponseDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @Operation(summary = "로그인", description = "이메일, 패스워드를 입력하여 로그인을 합니다. 결과로 accessToken, refreshToken 반환", responses = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "404", description = "찾을 수 없는 회원")
    })
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> login(@Valid @RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        TokenDto tokenDto = authService.login(memberLoginRequestDto);
        ResultResponse result = ResultResponse.of(ResultCode.LOGIN_SUCCESS, tokenDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @Operation(summary = "토큰 재발급", description = "Access Token(30분), Refresh Token(7일)의 유효기간이 지났을 때, 재발급 합니다. HTTP Header Authentication에 'Bearer {accessToken}'이 있어야 하고," +
            "요청 parameter로 accessToken, refreshToken을 넣어줘야 함.", responses = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "400", description = "권한이 없거나 유효하지 않은 토큰")
    })
    @PostMapping("/reissue")
    public ResponseEntity<ResultResponse> reissue(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = authService.reissue(tokenRequestDto);
        ResultResponse result = ResultResponse.of(ResultCode.REISSUE_SUCCESS, tokenDto);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @Operation(summary = "로그아웃", description = "로그인 상태에서 로그아웃을 합니다. HTTP Header Authentication에 'Bearer {accessToken}'이 있어야 하고, 요청 parameter로 refreshToken을 넣어줘야 함.",
        responses = {
                @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
                @ApiResponse(responseCode = "400", description = "권한이 없거나 유효하지 않은 토큰")
        }
    )
    @PostMapping("/logout")
    public ResponseEntity<ResultResponse> logout(@Valid @RequestBody MemberLogoutRequestDto memberLogoutRequestDto) {
        String refreshToken = memberLogoutRequestDto.getRefreshToken();
        authService.logout(refreshToken);
        ResultResponse result = ResultResponse.of(ResultCode.LOGOUT_SUCCESS, null);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
