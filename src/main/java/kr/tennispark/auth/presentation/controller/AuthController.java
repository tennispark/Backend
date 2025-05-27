package kr.tennispark.auth.presentation.controller;

import jakarta.validation.Valid;
import kr.tennispark.auth.application.service.AuthService;
import kr.tennispark.auth.presentation.dto.request.SendAuthCodeRequest;
import kr.tennispark.auth.presentation.dto.request.VerifyPhoneRequest;
import kr.tennispark.auth.presentation.dto.response.RegisterMemberResponse;
import kr.tennispark.auth.presentation.dto.response.VerifyPhoneResponse;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.user.presentation.dto.request.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResult<RegisterMemberResponse>> registerMember(
            @Valid @RequestBody RegisterMemberRequest request) {
        RegisterMemberResponse response = authService.registerMember(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiUtils.success(response));
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
}
