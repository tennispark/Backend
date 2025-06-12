package kr.tennispark.point.common.domain.exception;

import kr.tennispark.common.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class OutOfStockException extends RuntimeException {

    private final String message = "상품 수량이 부족합니다. 관리에게 문의해주세요.";

    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(HttpStatus.BAD_REQUEST, message);
    }

    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }
}
