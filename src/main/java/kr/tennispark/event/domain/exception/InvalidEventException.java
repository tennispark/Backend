package kr.tennispark.event.domain.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class InvalidEventException extends InvalidException {

    private static final String DEFAULT_MESSAGE = "유효하지 않은 이벤트입니다.";

    public InvalidEventException(String message) {
        super(message);
    }

    public InvalidEventException() {
        super(DEFAULT_MESSAGE);
    }
}
