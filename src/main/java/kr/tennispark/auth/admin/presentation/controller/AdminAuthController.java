package kr.tennispark.auth.admin.presentation.controller;

import kr.tennispark.auth.admin.application.service.AdminAuthService;
import kr.tennispark.auth.admin.presentation.dto.requeest.AdminLoginRequest;
import kr.tennispark.auth.admin.presentation.dto.response.AdminLoginResponse;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<ApiResult<AdminLoginResponse>> login(@RequestBody AdminLoginRequest request) {
        AdminLoginResponse response = adminAuthService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(response));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<ApiResult<AdminLoginResponse>> reissueLoginTokens(
            @RequestHeader("Refresh-Token") String refreshToken) {
        AdminLoginResponse response = adminAuthService.reissueLoginTokens(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResult<String>> logout(
            @RequestHeader("Refresh-Token") String refreshToken) {
        adminAuthService.logout(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success());
    }
}
