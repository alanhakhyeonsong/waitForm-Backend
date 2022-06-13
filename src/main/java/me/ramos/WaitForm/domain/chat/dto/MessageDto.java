package me.ramos.WaitForm.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ramos.WaitForm.domain.chat.entity.Message;
import me.ramos.WaitForm.domain.member.dto.MemberResponseDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Long roomId;
    private Long messageId;
    private MemberResponseDto sender;
    private String content;

    public MessageDto(Message message) {
        this.roomId = message.getRoom().getId();
        this.messageId = message.getId();
        this.sender = MemberResponseDto.of(message.getMember());
        this.content = message.getContents();
    }
}
