package kr.tennispark.activity.user.presentation.exception;

import kr.tennispark.activity.common.domain.exception.CapacityExceededException;
import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserActivityExceptionHandler {

    @ExceptionHandler(CapacityExceededException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> handleCapacityExceededException(CapacityExceededException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
