package kr.tennispark.auth.presentation.dto.response;

public record RegisterMemberResponse(
        String accessToken,
        String refreshToken
) {
}
