package kr.tennispark.common.exception;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.stream.Collectors;
import kr.tennispark.auth.application.exception.ExpiredTokenException;
import kr.tennispark.common.exception.base.InvalidException;
import kr.tennispark.common.exception.base.NotFoundException;
import kr.tennispark.common.exception.base.UnsupportedTypeException;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
                .body("유효하지 않은 토큰입니다.");
    }
}
