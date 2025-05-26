package kr.tennispark.activity.common.domain.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class InvalidActivityTimeException extends InvalidException {

    private static final String MESSAGE = "유효하지 않은 시간입니다.";

    public InvalidActivityTimeException(final String message) {
        super(message);
    }

    public InvalidActivityTimeException() {
        this(MESSAGE);
    }
}
