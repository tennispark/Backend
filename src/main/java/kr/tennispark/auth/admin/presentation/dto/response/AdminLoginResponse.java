package kr.tennispark.auth.admin.presentation.dto.response;

public record AdminLoginResponse(String accessToken, String refreshToken, String role, String region) {

    public static AdminLoginResponse of(String accessToken, String refreshToken, String role, String region) {
        return new AdminLoginResponse(accessToken, refreshToken, role, region);
    }
}