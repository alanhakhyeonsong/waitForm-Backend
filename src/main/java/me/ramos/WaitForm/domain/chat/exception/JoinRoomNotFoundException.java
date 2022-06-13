package me.ramos.WaitForm.domain.chat.exception;

import me.ramos.WaitForm.global.error.ErrorCode;
import me.ramos.WaitForm.global.error.exception.BusinessException;

public class JoinRoomNotFoundException extends BusinessException {

    public JoinRoomNotFoundException() {
        super(ErrorCode.JOIN_ROOM_NOT_FOUND);
    }
}
