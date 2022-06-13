package me.ramos.WaitForm.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCreateRequest {

    @NotNull
    private String host;

    @NotNull
    private String invited;
}