package me.ramos.WaitForm.global.error.exception;

import me.ramos.WaitForm.global.error.ErrorCode;

public class EntityAlreadyExistException extends BusinessException {

    public EntityAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
