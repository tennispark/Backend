package kr.tennispark.membership.user.presentation.exception;

import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.membership.common.domain.exception.InvalidActivityDurationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MembershipExceptionHandler {

    @ExceptionHandler(InvalidActivityDurationException.class)
    public ResponseEntity<ApiResult<?>> handleInvalidActivityDurationException(InvalidActivityDurationException exception){
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
