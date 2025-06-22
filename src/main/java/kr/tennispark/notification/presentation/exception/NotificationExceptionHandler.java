package kr.tennispark.notification.presentation.exception;

import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.notification.application.exception.FcmMessageSendFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotificationExceptionHandler {

    @ExceptionHandler(FcmMessageSendFailureException.class)
    public ResponseEntity<ApiResult<String>> handleFcmMessageSendFailureException(
            FcmMessageSendFailureException e) {
        return ResponseEntity.status(e.status()).body((ApiUtils.error(e.status(), e.getMessage())));
    }
}
