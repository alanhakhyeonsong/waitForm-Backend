package me.ramos.WaitForm.global.error.exception;

import me.ramos.WaitForm.global.error.ErrorCode;

public class NoAuthorityException extends BusinessException {
    public NoAuthorityException() {
        super(ErrorCode.NO_AUTHORITY);
    }
}
