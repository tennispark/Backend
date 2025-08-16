package kr.tennispark.common.exception;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.stream.Collectors;
import kr.tennispark.auth.admin.application.exception.UnauthorizedRoleAccessException;
import kr.tennispark.auth.common.application.exception.ExpiredTokenException;
import kr.tennispark.common.exception.base.DuplicateException;
import kr.tennispark.common.exception.base.InvalidException;
import kr.tennispark.common.exception.base.NotFoundException;
import kr.tennispark.common.exception.base.UnsupportedTypeException;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.notification.admin.application.exception.FcmMessageSendFailureException;
import kr.tennispark.notification.admin.infrastructure.exception.FirebaseInitializationException;
import kr.tennispark.qr.application.exception.BadQrCreateException;
import kr.tennispark.qr.application.exception.FailToCreateQRPayloadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ApiUtils.ApiResult<?>> handleExpiredTokenException(
            ExpiredTokenException exception) {
        return new ResponseEntity<>(exception.body(), exception.status());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<?> handleInvalidMemberException(InvalidException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> handleDuplicateException(DuplicateException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Domain validation failed: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiUtils.error(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResult<String>> handleAccessDenied(AccessDeniedException e) {
        log.warn("AccessDeniedException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiUtils.error(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."));
    }

    @ExceptionHandler(UnsupportedTypeException.class)
    public ResponseEntity<?> handleUnsupportedTypeException(UnsupportedTypeException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiUtils.error(HttpStatus.BAD_REQUEST, e.getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiUtils.ApiResult<Void> handleBindingError(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException e) {
            String field = e.getPath().stream()
                    .map(Reference::getFieldName)
                    .collect(Collectors.joining("."));

            String message = String.format("'%s'은(는) 필드 '%s'에 허용되지 않는 형식입니다.", e.getValue(), field);
            return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
        }

        return ApiUtils.error(HttpStatus.BAD_REQUEST, "요청 데이터를 읽을 수 없습니다.");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(JwtException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiUtils.error(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."));
    }

    @ExceptionHandler(FailToCreateQRPayloadException.class)
    public ResponseEntity<?> handleFailToCreateQRPayloadException(FailToCreateQRPayloadException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(BadQrCreateException.class)
    public ResponseEntity<?> handleBadQrCreateException(BadQrCreateException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedRoleAccessException.class)
    public ResponseEntity<?> handleMismatchedRoleTokenException(UnauthorizedRoleAccessException e) {
        return ResponseEntity.status(e.status()).body(ApiUtils.error(e.status(), e.getMessage()));
    }

    @ExceptionHandler(FcmMessageSendFailureException.class)
    public ResponseEntity<ApiResult<String>> handleFcmMessageSendFailureException(
            FcmMessageSendFailureException e) {
        return ResponseEntity.status(e.status()).body((ApiUtils.error(e.status(), e.getMessage())));
    }

    @ExceptionHandler(FirebaseInitializationException.class)
    public ResponseEntity<ApiResult<String>> handleFirebaseInitializationException(
            FirebaseInitializationException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResult<?>> handleIllegalState(IllegalStateException e) {
        log.error("시스템 상태 오류: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "요청 처리 중 오류가 발생했습니다."));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, NoResourceFoundException.class})
    public ResponseEntity<ApiResult<?>> handleWrongAPIException(
            Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiUtils.error(HttpStatus.NOT_FOUND, "존재하지 않는 API 입니다."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<?>> handleUnexpected(Exception e) {
        log.error("처리되지 않은 예외 발생", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiUtils.error(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다."));
    }
}
