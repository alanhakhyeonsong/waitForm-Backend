package me.ramos.WaitForm.domain.chat.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ramos.WaitForm.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JoinRoomDto {

    private Long roomId;
    private MessageDto lastMessage;
    private MemberInfoDto inviter;
    private List<MemberInfoDto> members = new ArrayList<>();

    @QueryProjection
    public JoinRoomDto(Long roomId, Member member) {
        this.roomId = roomId;
        this.inviter = new MemberInfoDto(member);
    }
}
