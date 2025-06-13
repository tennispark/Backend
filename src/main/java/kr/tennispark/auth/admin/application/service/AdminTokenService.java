package kr.tennispark.auth.admin.application.service;

import static kr.tennispark.common.constant.JwtConstants.ADMIN_ROLE_VALUE;

import kr.tennispark.auth.admin.application.exception.UnauthorizedRoleAccessException;
import kr.tennispark.auth.common.application.JwtTokenProvider;
import kr.tennispark.auth.common.application.dto.TokenDTO;
import kr.tennispark.auth.common.application.exception.ExpiredTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminTokenService {

    private static final String SUBJECT = "admin";

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminRedisBlacklistService blacklistService;

    public TokenDTO issueTokensFor() {
        TokenDTO tokens = TokenDTO.builder()
                .accessToken(jwtTokenProvider.createAccessToken(SUBJECT, ADMIN_ROLE_VALUE))
                .refreshToken(jwtTokenProvider.createRefreshToken(SUBJECT, ADMIN_ROLE_VALUE))
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
        if (!role.equals(ADMIN_ROLE_VALUE)) {
            throw new UnauthorizedRoleAccessException();
        }

        if (blacklistService.isBlacklisted(refreshToken)) {
            throw new ExpiredTokenException();
        }
    }

    public void expireTokens(String refreshToken) {
        blacklistService.saveBlacklist(refreshToken);
    }
}

