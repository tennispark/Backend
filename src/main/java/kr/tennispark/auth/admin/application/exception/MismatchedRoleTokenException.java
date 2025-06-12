package kr.tennispark.auth.admin.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MismatchedRoleTokenException extends RuntimeException {

    private final String message = "요구된 역할과 토큰의 역할이 일치하지 않습니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.FORBIDDEN, message);
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}

