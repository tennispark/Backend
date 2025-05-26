package kr.tennispark.members.common.domain.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class InvalidMemberException extends InvalidException {

    private static final String MESSAGE = "잘못된 회원의 정보입니다.";

    public InvalidMemberException() {
        super(MESSAGE);
    }

    public InvalidMemberException(String message) {
        super(message);
    }
}

