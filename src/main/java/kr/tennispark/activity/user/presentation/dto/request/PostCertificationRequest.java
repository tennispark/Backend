package kr.tennispark.activity.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PostCertificationRequest(
        @NotBlank(message = "인증 사진 URL은 필수입니다.")
        String imageUrl
) {
}
