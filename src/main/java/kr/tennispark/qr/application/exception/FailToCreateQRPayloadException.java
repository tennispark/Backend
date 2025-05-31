package kr.tennispark.qr.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class FailToCreateQRPayloadException extends RuntimeException {

    private static final String MESSAGE = "QR 페이로드 암호화/복호화 실패";

    public FailToCreateQRPayloadException(final String message) {
        super(message);
    }

    public FailToCreateQRPayloadException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
