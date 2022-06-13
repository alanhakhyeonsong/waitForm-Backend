package me.ramos.WaitForm.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.board.dto.BoardLikeResponseDto;
import me.ramos.WaitForm.domain.board.service.BoardService;
import me.ramos.WaitForm.global.result.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.ramos.WaitForm.global.result.ResultCode.FIND_BOARD_LIKE_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class BoardLikeController {

    private final BoardService boardService;

    // 게시글에 좋아요 눌린 목록 리스트 조회
    @Operation(summary = "게시글에 달린 좋아요 목록 조회", description = "Board의 PK 값을 기준으로 조회하는 API",
        responses = {
                @ApiResponse(responseCode = "200", description = "좋아요 조회 성공"),
                @ApiResponse(responseCode = "404", description = "해당 게시물에 달린 좋아요가 없습니다.")
        }
    )
    @GetMapping("/{boardId}")
    public ResponseEntity<ResultResponse> getLikes(@PathVariable Long boardId) {
        List<BoardLikeResponseDto> response = boardService.findLikesByBoard(boardId);

        ResultResponse result = ResultResponse.of(FIND_BOARD_LIKE_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    // 자신이 누른 좋아요와 게시글 리스트 조회
    @Operation(summary = "자신이 누른 좋아요 목록 조회", description = "로그인된 회원을 기준으로 조회하는 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "좋아요 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 게시물에 달린 좋아요가 없습니다.")
            }
    )
    @GetMapping("/me/list")
    public ResponseEntity<ResultResponse> getMyLikes() {
        List<BoardLikeResponseDto> response = boardService.getMyLikes();

        ResultResponse result = ResultResponse.of(FIND_BOARD_LIKE_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
