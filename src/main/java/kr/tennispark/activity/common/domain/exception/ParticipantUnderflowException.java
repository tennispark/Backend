package kr.tennispark.activity.common.domain.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class ParticipantUnderflowException extends RuntimeException {
    public static final String message = "활동 참가자는 0명 이상이어야 합니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
