package kr.tennispark.auth.admin.presentation.exception;

import kr.tennispark.auth.admin.application.exception.WrongAdminCredentialException;
import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminAuthExceptionHandler {

    @ExceptionHandler(WrongAdminCredentialException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> wrongAdminCredentialException(
            WrongAdminCredentialException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
