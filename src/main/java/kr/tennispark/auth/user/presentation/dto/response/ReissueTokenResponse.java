package kr.tennispark.auth.user.presentation.dto.response;

public record ReissueTokenResponse(
        String accessToken,
        String refreshToken
) {
}
