package me.ramos.WaitForm.domain.chat.repository.querydsl;

import me.ramos.WaitForm.domain.chat.dto.JoinRoomDto;

import java.util.List;

public interface JoinRoomQuerydslRepository {

    List<JoinRoomDto> findAllJoinRoomDtoByMemberId(Long memberId);
}
