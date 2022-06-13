package me.ramos.WaitForm.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import me.ramos.WaitForm.domain.chat.dto.*;
import me.ramos.WaitForm.domain.chat.service.ChatService;
import me.ramos.WaitForm.global.result.ResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static me.ramos.WaitForm.global.result.ResultCode.*;

@Validated
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // 채팅방 생성
    @Operation(summary = "채팅방 생성", description = "채팅방 생성 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "채팅방 생성 성공"),
                    @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "찾을 수 없는 회원"),
            }
    )
    @PostMapping("/chat/rooms")
    public ResponseEntity<ResultResponse> createChatRoom(@RequestBody ChatRoomCreateRequest request) {
        List<String> nicknames = new ArrayList<>();
        nicknames.add(request.getHost());
        nicknames.add(request.getInvited());
        ChatRoomCreateResponse response = chatService.createRoom(nicknames);

        return ResponseEntity.ok(ResultResponse.of(CREATE_CHAT_ROOM_SUCCESS, response));
    }

    @Operation(summary = "채팅방 목록 조회", description = "채팅방 목록 조회 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "채팅방 조회 성공"),
                    @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없습니다.")
            }
    )
    // 채팅방 목록 조회
    @GetMapping("/chat/rooms")
    public ResponseEntity<ResultResponse> getJoinRooms() {
        List<JoinRoomDto> response = chatService.getJoinRooms();

        return ResponseEntity.ok(ResultResponse.of(FIND_CHAT_ROOM_SUCCESS, response));
    }

    @Operation(summary = "채팅방 메시지 목록 조회", description = "채팅방 메시지 목록 조회 API",
            responses = {
                    @ApiResponse(responseCode = "200", description = "채팅방 메시지 목록 조회 성공"),
                    @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
                    @ApiResponse(responseCode = "404", description = "해당 채팅방에 참여하지 않은 회원입니다.")
            }
    )
    // 채팅방 메시지 목록 조회
    @GetMapping("/chat/rooms/{roomId}/messages")
    public ResponseEntity<ResultResponse> getAllMessages(@NotNull(message = "채팅방 PK는 필수입니다.") @PathVariable Long roomId) {
        List<MessageDto> response = chatService.getAllMessages(roomId);

        return ResponseEntity.ok(ResultResponse.of(FIND_ALL_MESSAGES_SUCCESS, response));
    }

    // 메시지 전송
    @MessageMapping("/messages")
    public void sendMessage(@RequestBody MessageRequestDto request) {
        chatService.sendMessage(request);
    }
}
