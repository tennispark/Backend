package kr.tennispark.auth.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class MemberAlreadyExistsException extends RuntimeException {
    private final String message = "이미 가입된 회원입니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
