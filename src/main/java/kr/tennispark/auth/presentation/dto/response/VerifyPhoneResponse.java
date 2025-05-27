package kr.tennispark.auth.presentation.dto.response;

public record VerifyPhoneResponse(
        boolean registered,
        String accessToken,
        String refreshToken
) {
    public static VerifyPhoneResponse needSignUp() {
        return new VerifyPhoneResponse(false, null, null);
    }

    public static VerifyPhoneResponse login(String accessToken, String refreshToken) {
        return new VerifyPhoneResponse(true, accessToken, refreshToken);
    }
}
