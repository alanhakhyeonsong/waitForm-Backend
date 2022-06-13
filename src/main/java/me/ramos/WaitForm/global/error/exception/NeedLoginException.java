package me.ramos.WaitForm.global.error.exception;

import me.ramos.WaitForm.global.error.ErrorCode;

public class NeedLoginException extends BusinessException {
    public NeedLoginException() {
        super(ErrorCode.NEED_LOGIN);
    }
}
