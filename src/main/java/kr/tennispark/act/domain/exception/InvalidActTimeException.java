package kr.tennispark.act.domain.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class InvalidActTimeException extends InvalidException {

    private static final String MESSAGE = "유효하지 않은 시간입니다.";

    public InvalidActTimeException(final String message) {
        super(message);
    }

    public InvalidActTimeException() {
        this(MESSAGE);
    }
}
