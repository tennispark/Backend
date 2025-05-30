package kr.tennispark.event.admin.application.exception;

import kr.tennispark.common.exception.base.DuplicateException;

public class MoreThanEventLimitException extends DuplicateException {
    private static final String DEFAULT_MESSAGE = "이벤트는 최대 5개까지만 생성할 수 있습니다.";

    public MoreThanEventLimitException(String message) {
        super(message);
    }

    public MoreThanEventLimitException() {
        super(DEFAULT_MESSAGE);
    }
}
