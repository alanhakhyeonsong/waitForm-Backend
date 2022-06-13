package me.ramos.WaitForm.domain.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.board.dto.BoardEnrollRequestDto;
import me.ramos.WaitForm.domain.board.dto.BoardLikeResponseDto;
import me.ramos.WaitForm.domain.board.dto.BoardResponseDto;
import me.ramos.WaitForm.domain.board.service.BoardService;
import me.ramos.WaitForm.global.result.ResultCode;
import me.ramos.WaitForm.global.result.ResultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static me.ramos.WaitForm.global.result.ResultCode.LIKE_BOARD_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시글 등록", description = "게시글 등록 API(로그인된 회원만 가능)",
        responses = {
                @ApiResponse(responseCode = "200", description = "등록 성공"),
                @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
                @ApiResponse(responseCode = "404", description = "회원 정보가 없습니다.")
        }
    )
    @PostMapping("/upload")
    public ResponseEntity<ResultResponse> upload(@Valid @RequestBody BoardEnrollRequestDto requestDto) throws Exception {
        BoardResponseDto response = boardService.upload(requestDto);
        ResultResponse result = ResultResponse.of(ResultCode.BOARD_ENROLL_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }


    @Operation(summary = "게시글 삭제", description = "게시글 삭제 API. 타인의 글은 삭제할 수 없음",
            responses = {
                    @ApiResponse(responseCode = "200", description = "삭제 성공"),
                    @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "회원 정보가 없습니다.")
            }
    )
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResultResponse> delete(@PathVariable("boardId") Long boardId) {
        boardService.delete(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_BOARD_SUCCESS, null));
    }

    // 자신이 쓴 글 목록 조회
    @Operation(summary = "자신이 쓴 글 목록 조회", description = "게시글 리스트 조회 API(로그인된 회원만 가능)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "게시글 목록 조회 성공"),
                    @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "board not exist.")
            }
    )
    @GetMapping("/me/list")
    public ResponseEntity<ResultResponse> findAllMyBoards() {
        List<BoardResponseDto> response = boardService.getMyBoardList();
        ResultResponse result = ResultResponse.of(ResultCode.GET_BOARD_LIST_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    // 단일 조회(BoardId 필요함)
    @Operation(summary = "단일 게시글 상세 조회", description = "단일 게시글 상세 조회 API(로그인된 회원만 가능)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
                    @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "board not exist.")
            }
    )
    @GetMapping("/{boardId}")
    public ResponseEntity<ResultResponse> findOneDetail(@PathVariable("boardId") Long boardId) {
        BoardResponseDto response = boardService.findDetailInfo(boardId);
        ResultResponse result = ResultResponse.of(ResultCode.GET_BOARD_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    // 게시글 좋아요
    @Operation(summary = "게시글 좋아요", description = "게시글 좋아요 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "좋아요 성공"),
                    @ApiResponse(responseCode = "400", description = "이미 좋아요를 누른 회원")
            }
    )
    @PostMapping("/{boardId}/like")
    public ResponseEntity<ResultResponse> likeBoard(@PathVariable("boardId") Long boardId) {
        BoardLikeResponseDto response = boardService.likeBoard(boardId);
        ResultResponse result = ResultResponse.of(LIKE_BOARD_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
