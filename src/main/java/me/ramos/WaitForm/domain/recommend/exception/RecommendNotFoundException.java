package me.ramos.WaitForm.domain.recommend.exception;

import me.ramos.WaitForm.global.error.ErrorCode;
import me.ramos.WaitForm.global.error.exception.BusinessException;

public class RecommendNotFoundException extends BusinessException {

    public RecommendNotFoundException() {
        super(ErrorCode.RECOMMEND_NOT_FOUND);
    }
}
