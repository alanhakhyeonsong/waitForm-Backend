package me.ramos.WaitForm.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCreateResponse {

    private boolean status;
    private Long chatRoomId;
    private MemberInfoDto inviter;
    private List<MemberInfoDto> members = new ArrayList<>();
}
