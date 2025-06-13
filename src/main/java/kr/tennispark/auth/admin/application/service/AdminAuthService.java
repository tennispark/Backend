package kr.tennispark.auth.admin.application.service;

import kr.tennispark.auth.admin.application.exception.AdminLoginFailedException;
import kr.tennispark.auth.admin.infrastructure.config.AdminProps;
import kr.tennispark.auth.admin.presentation.dto.requeest.AdminLoginRequest;
import kr.tennispark.auth.admin.presentation.dto.response.AdminLoginResponse;
import kr.tennispark.auth.common.application.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminAuthService {

    private final AdminProps props;
    private final PasswordEncoder encoder;
    private final AdminTokenService tokenService;

    public AdminLoginResponse login(AdminLoginRequest request) {
        if (!props.id().equals(request.id()) || !encoder.matches(request.password(), props.password())) {
            throw new AdminLoginFailedException();
        }
        TokenDTO tokens = tokenService.issueTokensFor();
        return AdminLoginResponse.of(tokens.accessToken(), tokens.refreshToken());
    }

    public AdminLoginResponse reissueLoginTokens(String refreshToken) {
        TokenDTO tokens = tokenService.reissueTokens(refreshToken);
        return AdminLoginResponse.of(tokens.accessToken(), tokens.refreshToken());
    }

    public void logout(String refreshToken) {
        tokenService.expireTokens(refreshToken);
    }
}
