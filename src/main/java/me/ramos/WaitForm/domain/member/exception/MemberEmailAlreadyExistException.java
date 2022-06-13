package me.ramos.WaitForm.domain.member.exception;

import me.ramos.WaitForm.global.error.ErrorCode;
import me.ramos.WaitForm.global.error.exception.BusinessException;

public class MemberEmailAlreadyExistException extends BusinessException {
    public MemberEmailAlreadyExistException() {
        super(ErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
    }
}
