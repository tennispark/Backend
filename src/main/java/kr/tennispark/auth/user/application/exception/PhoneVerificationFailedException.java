package kr.tennispark.auth.user.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class PhoneVerificationFailedException extends RuntimeException {
    private final String message = "전화번호 인증에 실패하였습니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.UNAUTHORIZED, message);
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
