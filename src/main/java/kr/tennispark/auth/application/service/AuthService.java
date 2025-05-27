package kr.tennispark.auth.application.service;

import kr.tennispark.auth.application.JwtTokenProvider;
import kr.tennispark.auth.application.dto.TokenDTO;
import kr.tennispark.auth.application.exception.PhoneVerificationFailedException;
import kr.tennispark.auth.domain.vo.VerificationCode;
import kr.tennispark.auth.infrastructure.sms.SmsService;
import kr.tennispark.auth.presentation.dto.request.VerifyPhoneRequest;
import kr.tennispark.auth.presentation.dto.response.VerifyPhoneResponse;
import kr.tennispark.members.user.application.service.MemberService;
import kr.tennispark.members.user.presentation.dto.request.RegisterMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final SmsService smsService;
    private final RedisAuthService redisAuthService;

    public void sendAuthCode(String number) {
        VerificationCode code = VerificationCode.generateCode();
        smsService.sendSms(code.getValue(), number);
        redisAuthService.saveCode(number, code.getValue());
    }

    public VerifyPhoneResponse verifyPhone(VerifyPhoneRequest req) {
        if (!redisAuthService.isCodeMatched(req.phoneNumber(), req.code())) {
            throw new PhoneVerificationFailedException();
        }

        if (memberService.existsMemberByPhone(req.phoneNumber())) {
            TokenDTO tokens = jwtTokenProvider.issueTokensFor(req.phoneNumber());
            return VerifyPhoneResponse.login(tokens.accessToken(), tokens.refreshToken());
        }
        return VerifyPhoneResponse.needSignUp();
    }

    private VerifyPhoneResponse loginFlow(String phoneNumber) {
        TokenDTO tokens = jwtTokenProvider.issueTokensFor(phoneNumber);
        return VerifyPhoneResponse.login(tokens.accessToken(), tokens.refreshToken());
    }

    public void registerMember(RegisterMemberRequest request) {
        // 1. 핸드폰 인증 완료 확인 (redis)
        // 2. 핸드폰 존재 여부 확인
        // 3. 회원가입
        memberService.createMember(request);
    }
}
