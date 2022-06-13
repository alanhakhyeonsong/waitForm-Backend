package me.ramos.WaitForm.domain.member.exception;

import me.ramos.WaitForm.global.error.ErrorCode;
import me.ramos.WaitForm.global.error.exception.BusinessException;

public class MemberNicknameAlreadyExistException extends BusinessException {

    public MemberNicknameAlreadyExistException() {
        super(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
    }
}
