package me.ramos.WaitForm.domain.member.exception;

import me.ramos.WaitForm.global.error.ErrorCode;
import me.ramos.WaitForm.global.error.exception.BusinessException;

public class MemberAlreadyLogoutException extends BusinessException {
    public MemberAlreadyLogoutException() {
        super(ErrorCode.MEMBER_ALREADY_LOGOUT);
    }
}
