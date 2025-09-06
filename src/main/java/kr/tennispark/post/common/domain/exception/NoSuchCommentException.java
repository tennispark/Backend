package kr.tennispark.post.common.domain.exception;

import kr.tennispark.common.exception.base.NotFoundException;

public class NoSuchCommentException extends NotFoundException {

    private static final String MESSAGE = "해당하는 댓글을 찾을 수 없습니다.";

    public NoSuchCommentException() {
        super(MESSAGE);
    }

    public NoSuchCommentException(String message) {
        super(message);
    }

}
