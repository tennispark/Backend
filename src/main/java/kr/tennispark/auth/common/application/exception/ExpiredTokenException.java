package kr.tennispark.auth.common.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends RuntimeException {
    private final String message = "토큰이 만료되었습니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.UNAUTHORIZED, message);
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
