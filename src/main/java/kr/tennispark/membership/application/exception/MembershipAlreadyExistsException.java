package kr.tennispark.membership.application.exception;

import kr.tennispark.common.exception.base.DuplicateException;

public class MembershipAlreadyExistsException extends DuplicateException {
    private static final String DEFAULT_MESSAGE = "이미 멤버십에 가입한 회원입니다.";

    public MembershipAlreadyExistsException(String message) {
        super(message);
    }

    public MembershipAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

}
