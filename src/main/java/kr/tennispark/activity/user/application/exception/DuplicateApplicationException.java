package kr.tennispark.activity.user.application.exception;

import kr.tennispark.common.exception.base.DuplicateException;

public class DuplicateApplicationException extends DuplicateException {
    private static final String DEFAULT_MESSAGE = "이미 신청한 활동입니다";

    public DuplicateApplicationException(String message) {
        super(message);
    }

    public DuplicateApplicationException() {
        super(DEFAULT_MESSAGE);
    }
}
