package me.ramos.WaitForm.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.member.dto.MemberResponseDto;
import me.ramos.WaitForm.domain.member.service.MemberService;
import me.ramos.WaitForm.global.result.ResultCode;
import me.ramos.WaitForm.global.result.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "로그인된 회원 정보 가져오기", description = "로그인된 상태에서 본인의 회원 정보를 가져옵니다. HTTP Header Authentication에 'Bearer {accessToken}'이 있어야 함.",
        responses = {
                @ApiResponse(responseCode = "200", description = "내 정보 조회 완료"),
                @ApiResponse(responseCode = "400", description = "권한이 없거나 유효하지 않은 토큰")
        }
    )
    @GetMapping("/me")
    public ResponseEntity<ResultResponse> getMyMemberInfo() {
        MemberResponseDto myInfo = memberService.getMyInfo();
        ResultResponse result = ResultResponse.of(ResultCode.GET_MY_INFO_SUCCESS, myInfo);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @Operation(summary = "nickname으로 회원 정보 조회", description = "HTTP Header Authentication에 'Bearer {accessToken}'이 있어야 함.",
        responses = {
                @ApiResponse(responseCode = "200", description = "회원 정보 조회 완료"),
                @ApiResponse(responseCode = "400", description = "권한이 없거나 유효하지 않은 토큰"),
                @ApiResponse(responseCode = "404", description = "찾을수 없는 회원")
        }
    )
    @GetMapping("/{nickname}")
    public ResponseEntity<ResultResponse> findByMemberNickname(@PathVariable("nickname") String nickname) {
        MemberResponseDto member = memberService.findByMemberNickname(nickname);
        ResultResponse result = ResultResponse.of(ResultCode.GET_MEMBER_INFO_SUCCESS, member);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
