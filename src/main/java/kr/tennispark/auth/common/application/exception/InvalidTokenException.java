package kr.tennispark.auth.common.application.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class InvalidTokenException extends InvalidException {

    public InvalidTokenException(final String message) {
        super(message);
    }

    public InvalidTokenException() {
        this("유효하지 않은 토큰입니다.");
    }
}
