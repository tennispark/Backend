package kr.tennispark.record.common.domain.entity.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class InvalidRecordException extends InvalidException {

    private static final String DEFAULT_MESSAGE = "유효하지 않은 기록입니다.";

    public InvalidRecordException(String message) {
        super(message);
    }

    public InvalidRecordException() {
        super(DEFAULT_MESSAGE);
    }
}
