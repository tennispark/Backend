package kr.tennispark.act.common.domain.exception;

import kr.tennispark.common.exception.base.NotFoundException;

public class NoSuchActException extends NotFoundException {

    private static final String MESSAGE = "해당하는 활동을 찾을 수 없습니다.";

    public NoSuchActException() {
        super(MESSAGE);
    }

    public NoSuchActException(String message) {
        super(message);
    }

}
