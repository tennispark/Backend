package kr.tennispark.auth.user.presentation.exception;

import kr.tennispark.auth.user.application.exception.MemberAlreadyExistsException;
import kr.tennispark.auth.user.application.exception.PhoneNotVerifiedException;
import kr.tennispark.auth.user.application.exception.PhoneVerificationFailedException;
import kr.tennispark.auth.user.infrastructure.exception.SmsSendFailedException;
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

    @ExceptionHandler(MemberAlreadyExistsException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> memberAlreadyExistsException(
            MemberAlreadyExistsException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(SmsSendFailedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> smsSendFailedException(
            SmsSendFailedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
