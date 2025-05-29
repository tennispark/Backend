package kr.tennispark.event.user.application.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class AlreadyAttendEventException extends InvalidException {
    public static final String MESSAGE = "이미 신청한 이벤트입니다.";

    public AlreadyAttendEventException() {
        super(MESSAGE);
    }

    public AlreadyAttendEventException(String message) {
        super(message);
    }
}
