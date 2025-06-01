package kr.tennispark.advertisement.admin.application.exception;

import kr.tennispark.common.exception.base.NotFoundException;

public class NoSuchAdvertisementException extends NotFoundException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 광고입니다.";

    public NoSuchAdvertisementException(String message) {
        super(message);
    }

    public NoSuchAdvertisementException() {
        super(DEFAULT_MESSAGE);
    }

}
