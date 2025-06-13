package kr.tennispark.auth.admin.presentation.exception;

import kr.tennispark.auth.admin.application.exception.AdminLoginFailedException;
import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminAuthExceptionHandler {

    @ExceptionHandler(AdminLoginFailedException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> wrongAdminCredentialException(
            AdminLoginFailedException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }
}
