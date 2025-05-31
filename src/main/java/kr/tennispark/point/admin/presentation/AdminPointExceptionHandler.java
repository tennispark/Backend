package kr.tennispark.point.admin.presentation;

import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.point.common.domain.exception.NotEnoughPointException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminPointExceptionHandler {

    @ExceptionHandler(NotEnoughPointException.class)
    public ResponseEntity<ApiResult<?>> handleNotEnoughPointException(NotEnoughPointException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

}
