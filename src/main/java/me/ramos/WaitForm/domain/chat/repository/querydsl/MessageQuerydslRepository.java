package me.ramos.WaitForm.domain.chat.repository.querydsl;

import me.ramos.WaitForm.domain.chat.dto.MessageDto;

import java.util.List;

public interface MessageQuerydslRepository {

    List<MessageDto> findAllMessageDtoByMemberIdAndRoomId(Long memberId, Long roomId);
}
