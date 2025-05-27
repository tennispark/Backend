package kr.tennispark.auth.presentation.controller;

import jakarta.validation.Valid;
import kr.tennispark.auth.application.service.AuthService;
import kr.tennispark.auth.presentation.dto.request.SendAuthCodeRequest;
import kr.tennispark.common.utils.ApiUtils;
import kr.tennispark.common.utils.ApiUtils.ApiResult;
import kr.tennispark.members.user.presentation.dto.request.LoginMemberRequest;
import kr.tennispark.members.user.presentation.dto.response.LoginMemberResponse;
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

    @PostMapping("/auth/code")
    public ResponseEntity<ApiResult<String>> sendAuthCode(@Valid @RequestBody SendAuthCodeRequest request) {
        authService.sendAuthCode(request.phoneNumber());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success());
    }

    public ResponseEntity<ApiResult<LoginMemberResponse>> login(@Valid @RequestBody LoginMemberRequest request) {
        LoginMemberResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiUtils.success(response));
    }

}
