package kr.tennispark.auth.user.presentation.dto.response;

public record RegisterMemberResponse(
        String accessToken,
        String refreshToken
) {
}
