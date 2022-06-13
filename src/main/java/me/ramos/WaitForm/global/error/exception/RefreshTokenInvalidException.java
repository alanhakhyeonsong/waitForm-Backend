package me.ramos.WaitForm.global.error.exception;

import me.ramos.WaitForm.global.error.ErrorCode;

public class RefreshTokenInvalidException extends BusinessException {
    public RefreshTokenInvalidException() {
        super(ErrorCode.REFRESH_TOKEN_INVALID);
    }
}
