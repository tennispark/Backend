package kr.tennispark.point.user.presentation.exception;

import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.point.user.application.exception.PointNegativeValueException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PointExceptionHandler {

    @ExceptionHandler(PointNegativeValueException.class)
    public ResponseEntity<ApiResult<?>> handlePointNegativeValueException(PointNegativeValueException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
