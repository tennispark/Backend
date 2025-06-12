package kr.tennispark.auth.admin.application.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class WrongAdminCredentialException extends RuntimeException {

    private final String message = "아이디 또는 비밀번호가 올바르지 않습니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.UNAUTHORIZED, message);
    }

    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
