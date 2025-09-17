package kr.tennispark.post.user.application.exception;

import kr.tennispark.common.exception.base.InvalidException;

public class InvalidSearchKeywordException extends InvalidException {

    public InvalidSearchKeywordException(final String message) {
        super(message);
    }

    public InvalidSearchKeywordException() {
        this("검색어는 2자 이상이어야 합니다.");
    }
}
