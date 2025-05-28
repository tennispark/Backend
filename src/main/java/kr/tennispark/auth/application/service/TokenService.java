package kr.tennispark.auth.application.service;

import kr.tennispark.auth.application.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTokenService redisTokenService;


    public TokenDTO issueTokensFor(String payload) {
        // authority를 유지할 지 논의 필요
        return TokenDTO.builder()
                .accessToken(jwtTokenProvider.createAccessToken(String.valueOf(payload), "USER"))
                .refreshToken(jwtTokenProvider.createRefreshToken(String.valueOf(payload), "USER"))
                .build();
    }

}
