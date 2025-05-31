package kr.tennispark.point.user.application.exception;

import kr.tennispark.activity.common.domain.enums.ActivityName;
import kr.tennispark.activity.common.domain.enums.ActivityType;
import kr.tennispark.common.exception.base.InvalidException;
import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class PointNegativeValueException extends InvalidException {
    private static final String DEFAULT_MESSAGE = "포인트 연산은 양수만 가능합니다.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_MESSAGE);
    }

    public HttpStatus status() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
