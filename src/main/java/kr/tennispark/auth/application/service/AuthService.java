package kr.tennispark.auth.application.service;

import kr.tennispark.auth.application.JwtTokenProvider;
import kr.tennispark.auth.application.dto.TokenDTO;
import kr.tennispark.auth.application.exception.PhoneVerificationFailedException;
import kr.tennispark.auth.domain.vo.VerificationCode;
import kr.tennispark.auth.infrastructure.sms.SmsService;
import kr.tennispark.auth.presentation.dto.request.VerifyPhoneRequest;
import kr.tennispark.auth.presentation.dto.response.VerifyPhoneResponse;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
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

        return memberRepository.findByPhone_Number(req.phoneNumber())
                .map(this::loginFlow)
                .orElseGet(VerifyPhoneResponse::needSignUp);
    }

    private VerifyPhoneResponse loginFlow(Member member) {
        TokenDTO tokens = jwtTokenProvider.issueTokensFor(member);
        return VerifyPhoneResponse.login(tokens.accessToken(), tokens.refreshToken());
    }
}
