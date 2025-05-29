package kr.tennispark.common.qr.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class BadQrCreateException extends RuntimeException {

    private static final String MESSAGE = "QR 생성 또는 업로드 실패";

    public BadQrCreateException(final String message) {
        super(message);
    }

    public BadQrCreateException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
