package kr.tennispark.auth.admin.application.service;

import kr.tennispark.auth.admin.application.exception.MismatchedRoleTokenException;
import kr.tennispark.auth.common.application.JwtTokenProvider;
import kr.tennispark.auth.common.application.dto.TokenDTO;
import kr.tennispark.auth.common.application.exception.ExpiredTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminTokenService {

    private static final String SUBJECT = "admin";
    private static final String ROLE = "ADMIN";

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminRedisBlacklistService blacklistService;

    public TokenDTO issueTokensFor() {
        TokenDTO tokens = TokenDTO.builder()
                .accessToken(jwtTokenProvider.createAccessToken(SUBJECT, ROLE))
                .refreshToken(jwtTokenProvider.createRefreshToken(SUBJECT, ROLE))
                .build();
        return tokens;
    }

    public TokenDTO reissueTokens(String refreshToken) {
        validate(refreshToken);
        blacklistService.saveBlacklist(refreshToken);
        return issueTokensFor();
    }

    private void validate(String refreshToken) {
        String role = jwtTokenProvider.getRole(refreshToken);
        if (!role.equals(ROLE)) {
            throw new MismatchedRoleTokenException();
        }

        if (blacklistService.isBlacklisted(refreshToken)) {
            throw new ExpiredTokenException();
        }
    }

    public void expireTokens(String refreshToken) {
        blacklistService.saveBlacklist(refreshToken);
    }
}

