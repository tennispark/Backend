package kr.tennispark.post.user.application.exception;

import kr.tennispark.common.exception.base.NotAuthorizedException;

public class NotAuthorizedCommentException extends NotAuthorizedException {

    public static final String MESSAGE = "해당 댓글에 대한 접근 권한이 없습니다.";

    public NotAuthorizedCommentException() {
        super(MESSAGE);
    }

    public NotAuthorizedCommentException(String message) {
        super(message);
    }
}
