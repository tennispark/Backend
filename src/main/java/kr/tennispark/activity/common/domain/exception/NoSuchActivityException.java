package kr.tennispark.activity.common.domain.exception;

import kr.tennispark.common.exception.base.NotFoundException;

public class NoSuchActivityException extends NotFoundException {

    private static final String MESSAGE = "해당하는 활동을 찾을 수 없습니다.";

    public NoSuchActivityException() {
        super(MESSAGE);
    }

    public NoSuchActivityException(String message) {
        super(message);
    }

}
