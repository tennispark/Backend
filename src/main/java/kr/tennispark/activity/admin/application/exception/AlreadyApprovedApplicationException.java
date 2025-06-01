package kr.tennispark.activity.admin.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class AlreadyApprovedApplicationException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "이미 승인된 신청입니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, DEFAULT_MESSAGE);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
