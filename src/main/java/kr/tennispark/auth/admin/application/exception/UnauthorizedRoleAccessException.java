package kr.tennispark.auth.admin.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class UnauthorizedRoleAccessException extends RuntimeException {

    private final String message = "권한이 없는 사용자입니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.FORBIDDEN, message);
    }

    public HttpStatus status() {
        return HttpStatus.FORBIDDEN;
    }
}

