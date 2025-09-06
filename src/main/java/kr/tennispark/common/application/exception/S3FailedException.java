package kr.tennispark.common.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class S3FailedException extends RuntimeException {

    private static final String MESSAGE = "S3 작업 실패";

    public S3FailedException(final String message) {
        super(message);
    }

    public S3FailedException() {
        this(MESSAGE);
    }

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, getMessage());
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
