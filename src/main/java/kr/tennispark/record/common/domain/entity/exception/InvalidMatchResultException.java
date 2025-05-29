package kr.tennispark.record.common.domain.entity.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class InvalidMatchResultException extends InvalidException {

    private static final String DEFAULT_MESSAGE = "유효하지 않은 기록입니다.";

    public InvalidMatchResultException(String message) {
        super(message);
    }

    public InvalidMatchResultException() {
        super(DEFAULT_MESSAGE);
    }
}
