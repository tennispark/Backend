package kr.tennispark.web.member.presentation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public record UpdateCouponRequest(
        @NotNull(message = "쿠폰 수는 필수입니다.")
        @DecimalMin(value = "0.0", inclusive = true, message = "쿠폰 수는 0 이상이어야 합니다.")
        @Digits(integer = 6, fraction = 1, message = "쿠폰 수는 소숫점 1자리까지 입력 가능합니다.")
        Double coupon
) {
}
