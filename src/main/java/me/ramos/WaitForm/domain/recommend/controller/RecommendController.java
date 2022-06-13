package me.ramos.WaitForm.domain.recommend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.recommend.dto.RecommendRequest;
import me.ramos.WaitForm.domain.recommend.dto.RecommendResponse;
import me.ramos.WaitForm.domain.recommend.service.RecommendService;
import me.ramos.WaitForm.global.result.ResultCode;
import me.ramos.WaitForm.global.result.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {

    private final RecommendService recommendService;

    @Operation(summary = "추천 등록(clustering된 member들의 id와(리스트 형태) 해당 게시글 id를 넣어 보내줘야 함)", description = "추천 등록 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "추천 등록 성공"),
                    @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "board not exist."),
                    @ApiResponse(responseCode = "404", description = "member not exist.")
            }
    )
    @PostMapping
    public ResponseEntity<ResultResponse> enroll(@RequestBody RecommendRequest request) {
        recommendService.save(request);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.ENROLL_RECOMMEND_SUCCESS, null));
    }

    @Operation(summary = "나의 추천 목록 조회", description = "추천 목록 조회 API(로그인된 회원만 가능)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "추천 조회 성공"),
                    @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "추천 목록을 찾을 수 없습니다.")
            }
    )
    // 회원 기준으로 조회
    @GetMapping("/list")
    public ResponseEntity<ResultResponse> findAllMyRecommends() {
        List<RecommendResponse> response = recommendService.getMyRecommends();
        ResultResponse result = ResultResponse.of(ResultCode.FIND_RECOMMENDS_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
