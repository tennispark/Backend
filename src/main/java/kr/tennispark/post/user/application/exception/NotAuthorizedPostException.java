package kr.tennispark.post.user.application.exception;

import kr.tennispark.common.exception.base.NotAuthorizedException;

public class NotAuthorizedPostException extends NotAuthorizedException {

    public static final String MESSAGE = "해당 게시물에 대한 접근 권한이 없습니다.";

    public NotAuthorizedPostException() {
        super(MESSAGE);
    }

    public NotAuthorizedPostException(String message) {
        super(message);
    }
}
