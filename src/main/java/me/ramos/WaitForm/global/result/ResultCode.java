package me.ramos.WaitForm.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    // Member
    REGISTER_SUCCESS(200, "M001", "회원가입 되었습니다."),
    LOGIN_SUCCESS(200, "M002", "로그인 되었습니다."),
    REISSUE_SUCCESS(200, "M003", "재발급 되었습니다."),
    LOGOUT_SUCCESS(200, "M004", "로그아웃 되었습니다."),
    GET_MY_INFO_SUCCESS(200, "M005", "내 정보 조회 완료"),
    GET_MEMBER_INFO_SUCCESS(200, "M006", "회원 정보 조회 완료"),

    // Board
    BOARD_ENROLL_SUCCESS(200, "B001", "게시글이 등록되었습니다."),
    GET_BOARD_SUCCESS(200, "B002", "게시글 조회 성공"),
    GET_BOARD_LIST_SUCCESS(200, "B003", "게시글 목록 조회 성공"),
    DELETE_BOARD_SUCCESS(200, "B004", "게시글 삭제 성공"),
    LIKE_BOARD_SUCCESS(200, "B005", "게시글 좋아요 성공"),

    // BoardLike
    FIND_BOARD_LIKE_SUCCESS(200, "BL001", "좋아요 조회 성공"),

    // Chat
    CREATE_CHAT_ROOM_SUCCESS(200, "C001", "채팅방 생성 성공"),
    FIND_CHAT_ROOM_SUCCESS(200, "C002", "채팅방 조회 성공"),
    FIND_ALL_MESSAGES_SUCCESS(200, "C003", "채팅방 전체 메시지 조회 성공"),

    // Recommend
    ENROLL_RECOMMEND_SUCCESS(200, "R001", "추천 등록 성공"),
    FIND_RECOMMENDS_SUCCESS(200, "R002", "추천 목록 조회 성공");

    private int status;
    private final String code;
    private final String message;
}
