package kr.tennispark.advertisement.admin.application.exception;

import kr.tennispark.common.exception.base.DuplicateException;

public class AdvertisementLimitExceededException extends DuplicateException {
    private static final String DEFAULT_MESSAGE = "이미 해당 위치에 광고가 3개 이상 등록되어 있습니다.";

    public AdvertisementLimitExceededException(String message) {
        super(message);
    }

    public AdvertisementLimitExceededException() {
        super(DEFAULT_MESSAGE);
    }

}
