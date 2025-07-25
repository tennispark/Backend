package kr.tennispark.auth.user.presentation.controller;

import jakarta.validation.Valid;
import kr.tennispark.auth.user.application.service.AuthService;
import kr.tennispark.auth.user.presentation.dto.request.SendAuthCodeRequest;
import kr.tennispark.auth.user.presentation.dto.request.VerifyPhoneRequest;
import kr.tennispark.auth.user.presentation.dto.response.RegisterMemberResponse;
import kr.tennispark.auth.user.presentation.dto.response.ReissueTokenResponse;
import kr.tennispark.auth.user.presentation.dto.response.VerifyPhoneResponse;
import kr.tennispark.common.annotation.LoginMember;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.presentation.dto.request.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResult<RegisterMemberResponse>> registerMember(
            @Valid @RequestBody RegisterMemberRequest request) {
        RegisterMemberResponse response = authService.registerMember(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success(response));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResult<String>> withdrawMember(@LoginMember Member member) {
        authService.withdrawMember(member);
        return ResponseEntity.ok(ApiUtils.success());
    }

    @PostMapping("/auth/phones/code")
    public ResponseEntity<ApiResult<String>> sendAuthCode(@Valid @RequestBody SendAuthCodeRequest request) {
        authService.sendAuthCode(request.phoneNumber());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success());
    }

    @PostMapping("/auth/phones/code/verify")
    public ResponseEntity<ApiResult<VerifyPhoneResponse>> verifyPhone(@Valid @RequestBody VerifyPhoneRequest request) {
        VerifyPhoneResponse response = authService.verifyPhone(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(response));
    }

    @PostMapping("/auth/token/refresh")
    public ResponseEntity<ApiResult<ReissueTokenResponse>> reissueLoginTokens(
            @RequestHeader("Refresh-Token") String refreshToken) {
        ReissueTokenResponse response = authService.reissueLoginTokens(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(response));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResult<String>> logout(@LoginMember Member member) {
        authService.logout(member);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success());
    }
}
