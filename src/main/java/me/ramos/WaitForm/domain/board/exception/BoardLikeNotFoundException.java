package me.ramos.WaitForm.domain.board.exception;

import me.ramos.WaitForm.global.error.ErrorCode;
import me.ramos.WaitForm.global.error.exception.BusinessException;

public class BoardLikeNotFoundException extends BusinessException {
    public BoardLikeNotFoundException() {
        super(ErrorCode.BOARD_LIKE_NOT_EXIST);
    }
}
