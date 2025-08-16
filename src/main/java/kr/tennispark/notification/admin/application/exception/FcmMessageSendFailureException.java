package kr.tennispark.notification.admin.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class FcmMessageSendFailureException extends RuntimeException {

    private final static String DEFAULT_MESSAGE = "FCM 알림 전송에 실패하였습니다.";

    public FcmMessageSendFailureException(final String message) {
        super(message);
    }

    public FcmMessageSendFailureException() {
        this(DEFAULT_MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
