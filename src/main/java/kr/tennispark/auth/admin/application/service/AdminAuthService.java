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
        AdminProps.AdminAccount account = props.admins().stream()
                .filter(a -> a.id().equals(request.id()))
                .filter(a -> encoder.matches(request.password(), a.password()))
                .findFirst()
                .orElseThrow(AdminLoginFailedException::new);

        TokenDTO tokens = tokenService.issueTokensFor();
        return AdminLoginResponse.of(tokens.accessToken(), tokens.refreshToken(), account.role(), account.region());
    }

    public AdminLoginResponse reissueLoginTokens(String refreshToken) {
        TokenDTO tokens = tokenService.reissueTokens(refreshToken);
        return AdminLoginResponse.of(tokens.accessToken(), tokens.refreshToken(), null, null);
    }

    public void logout(String refreshToken) {
        tokenService.expireTokens(refreshToken);
    }
}