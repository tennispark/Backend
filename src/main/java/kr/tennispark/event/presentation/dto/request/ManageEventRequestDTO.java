package kr.tennispark.event.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ManageEventRequestDTO(
        @NotBlank(message = "이벤트 제목은 필수입니다.") String title,
        @NotBlank(message = "이벤트 내용은 필수입니다.") String content,
        @NotNull(message = "이벤트 지급 포인트는 필수입니다.") @Min(0) Integer point
) {
}
