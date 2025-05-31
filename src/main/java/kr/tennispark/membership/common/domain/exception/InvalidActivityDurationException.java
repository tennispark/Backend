package kr.tennispark.membership.common.domain.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class InvalidActivityDurationException extends InvalidException {
    private static final String DEFAULT_MESSAGE = "지원하지 않는 활동 기간입니다.";

    public InvalidActivityDurationException(String message) {
        super(message);
    }

    public InvalidActivityDurationException() {
        super(DEFAULT_MESSAGE);
    }
}
