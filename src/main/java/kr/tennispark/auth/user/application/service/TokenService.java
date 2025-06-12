package kr.tennispark.auth.user.application.service;

import kr.tennispark.auth.common.application.JwtTokenProvider;
import kr.tennispark.auth.common.application.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenService redisTokenService;

    public TokenDTO issueTokensFor(String phoneNumber) {
        TokenDTO tokens = TokenDTO.builder()
                .accessToken(jwtTokenProvider.createAccessToken(phoneNumber, "USER"))
                .refreshToken(jwtTokenProvider.createRefreshToken(phoneNumber, "USER"))
                .build();
        redisTokenService.saveRefreshToken(phoneNumber, tokens.refreshToken());
        return tokens;
    }

    public TokenDTO reissueTokens(String refreshToken) {
        String userPhone = jwtTokenProvider.getSubject(refreshToken);

        redisTokenService.validateRefreshToken(userPhone, refreshToken);
        return issueTokensFor(userPhone);
    }

    public void expireTokens(String phoneNumber) {
        redisTokenService.deleteRefreshToken(phoneNumber);
    }
}
