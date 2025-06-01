package kr.tennispark.activity.admin.presentation.exception;

import kr.tennispark.activity.admin.application.exception.AlreadyApprovedApplicationException;
import kr.tennispark.activity.admin.application.exception.AlreadyCanceledApplicationException;
import kr.tennispark.common.utils.ApiUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminActivityExceptionHandler {

    @ExceptionHandler(AlreadyApprovedApplicationException.class)
    public ApiUtils.ApiResult<?> handleAlreadyApprovedApplicationException(
            AlreadyApprovedApplicationException exception) {
        return exception.body();
    }

    @ExceptionHandler(AlreadyCanceledApplicationException.class)
    public ApiUtils.ApiResult<?> handleAlreadyCanceledApplicationException(
            AlreadyCanceledApplicationException exception) {
        return exception.body();
    }
}
