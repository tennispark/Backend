package kr.tennispark.point.common.domain.exception;

import kr.tennispark.common.exception.base.NotFoundException;

public class NoSuchProductException extends NotFoundException {

    private static final String MESSAGE = "해당하는 상품을 찾을 수 없습니다.";

    public NoSuchProductException() {
        super(MESSAGE);
    }

    public NoSuchProductException(String message) {
        super(message);
    }

}
