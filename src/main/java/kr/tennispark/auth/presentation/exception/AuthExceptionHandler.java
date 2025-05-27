package kr.tennispark.auth.presentation.exception;

import kr.tennispark.auth.application.exception.PhoneNotVerifiedException;
import kr.tennispark.auth.application.exception.PhoneVerificationFailedException;
import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(PhoneVerificationFailedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> phoneVerificationFailedException(
            PhoneVerificationFailedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(PhoneNotVerifiedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> phoneNotVerifiedException(
            PhoneNotVerifiedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
