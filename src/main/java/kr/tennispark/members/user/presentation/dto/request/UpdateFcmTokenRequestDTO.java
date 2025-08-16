package kr.tennispark.members.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateFcmTokenRequestDTO(
        @NotBlank(message = "토큰값은 필수입니다.")
        String fcmToken
) {
}
