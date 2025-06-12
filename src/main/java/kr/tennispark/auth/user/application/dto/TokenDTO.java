package kr.tennispark.auth.user.application.dto;

import lombok.Builder;

@Builder
public record TokenDTO(
        String refreshToken,
        String accessToken
) {
}
