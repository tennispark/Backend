package kr.tennispark.notification.admin.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record SendMessageRequestDTO(
        @NotBlank(message = "제목은 필수값입니다.")
        String title,

        @NotBlank(message = "내용은 필수값입니다.")
        String content) {
}
