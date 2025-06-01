package kr.tennispark.event.common.domain.exception;

import kr.tennispark.common.exception.base.NotFoundException;

public class NoSuchEventException extends NotFoundException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 이벤트입니다.";

    public NoSuchEventException(String message) {
        super(message);
    }

    public NoSuchEventException() {
        super(DEFAULT_MESSAGE);
    }

}
