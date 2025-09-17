package kr.tennispark.post.common.domain.exception;

import kr.tennispark.common.exception.base.NotFoundException;

public class NoSuchPostException extends NotFoundException {

    private static final String MESSAGE = "해당하는 게시물을 찾을 수 없습니다.";

    public NoSuchPostException() {
        super(MESSAGE);
    }

    public NoSuchPostException(String message) {
        super(message);
    }

}
