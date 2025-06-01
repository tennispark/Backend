package kr.tennispark.activity.admin.application.exception;

import kr.tennispark.common.exception.base.NotFoundException;

public class NoSuchActivityApplicationException extends NotFoundException {

    private static final String MESSAGE = "해당 활동 신청이 존재하지 않습니다.";

    public NoSuchActivityApplicationException() {
        super(MESSAGE);
    }

    public NoSuchActivityApplicationException(String message) {
        super(message);
    }
}
