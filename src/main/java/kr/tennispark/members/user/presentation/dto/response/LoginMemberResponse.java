package kr.tennispark.members.user.presentation.dto.response;

public record LoginMemberResponse(String accessToken,
                                  String refreshToken) {
    public static LoginMemberResponse of(
            String accessToken,
            String refreshToken
    ) {
        return new LoginMemberResponse(accessToken, refreshToken);
    }
}