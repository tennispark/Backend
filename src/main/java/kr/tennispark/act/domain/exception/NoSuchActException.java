package kr.tennispark.act.domain.exception;

import kr.tennispark.common.exception.base.NotFoundException;

public class NoSuchActException extends NotFoundException {

    private static final String MESSAGE = "존재하지 않는 활동입니다.";

    public NoSuchActException() {
        super(MESSAGE);
    }

    public NoSuchActException(String message) {
        super(message);
    }

}
