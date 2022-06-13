package me.ramos.WaitForm.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ramos.WaitForm.domain.member.entity.Member;

@Getter
@NoArgsConstructor
public class MemberInfoDto {

    private String nickname;

    public MemberInfoDto(Member member) {
        this.nickname = member.getNickname();
    }
}
