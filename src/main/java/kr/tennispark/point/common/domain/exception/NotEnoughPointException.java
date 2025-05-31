package kr.tennispark.point.common.domain.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class NotEnoughPointException extends RuntimeException {

    private final String message = "포인트가 부족합니다. 잔액을 확인해주세요.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
