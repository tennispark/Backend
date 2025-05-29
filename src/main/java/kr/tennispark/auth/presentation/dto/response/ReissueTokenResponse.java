package kr.tennispark.auth.presentation.dto.response;

public record ReissueTokenResponse(
        String accessToken,
        String refreshToken
) {
}
