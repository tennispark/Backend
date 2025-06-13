package kr.tennispark.auth.common.application.dto;

import lombok.Builder;

@Builder
public record TokenDTO(
        String refreshToken,
        String accessToken
) {
}
