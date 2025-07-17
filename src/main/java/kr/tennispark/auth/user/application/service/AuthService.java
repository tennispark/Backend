package kr.tennispark.auth.user.application.service;

import kr.tennispark.auth.common.application.dto.TokenDTO;
import kr.tennispark.auth.user.application.exception.MemberAlreadyExistsException;
import kr.tennispark.auth.user.application.exception.PhoneNotVerifiedException;
import kr.tennispark.auth.user.application.exception.PhoneVerificationFailedException;
import kr.tennispark.auth.user.domain.vo.VerificationCode;
import kr.tennispark.auth.user.infrastructure.sms.SmsService;
import kr.tennispark.auth.user.presentation.dto.request.VerifyPhoneRequest;
import kr.tennispark.auth.user.presentation.dto.response.RegisterMemberResponse;
import kr.tennispark.auth.user.presentation.dto.response.ReissueTokenResponse;
import kr.tennispark.auth.user.presentation.dto.response.VerifyPhoneResponse;
import kr.tennispark.members.common.domain.entity.Member;
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
    private final SmsService smsService;
    private final RedisAuthService redisAuthService;
    private final TokenService tokenService;

    @Transactional
    public RegisterMemberResponse registerMember(RegisterMemberRequest request) {
        if (!redisAuthService.isVerified(request.phoneNumber())) {
            throw new PhoneNotVerifiedException();
        }

        if (memberService.existsMemberByPhone(request.phoneNumber())) {
            throw new MemberAlreadyExistsException();
        }

        memberService.createMember(request);

        TokenDTO tokens = tokenService.issueTokensFor(request.phoneNumber());
        return new RegisterMemberResponse(tokens.accessToken(), tokens.refreshToken());
    }

    public void sendAuthCode(String number) {
        VerificationCode code = VerificationCode.generateCode();
        smsService.sendSms(number, code.getValue());
        redisAuthService.saveCode(number, code.getValue());
    }

    public VerifyPhoneResponse verifyPhone(VerifyPhoneRequest req) {
        if (!redisAuthService.isCodeMatched(req.phoneNumber(), req.code())) {
            throw new PhoneVerificationFailedException();
        }
        if (memberService.existsMemberByPhone(req.phoneNumber())) {
            TokenDTO tokens = tokenService.issueTokensFor(req.phoneNumber());
            return VerifyPhoneResponse.login(tokens.accessToken(), tokens.refreshToken());
        }

        redisAuthService.saveVerifiedStatus(req.phoneNumber());
        return VerifyPhoneResponse.needSignUp();
    }

    public ReissueTokenResponse reissueLoginTokens(String refreshToken) {
        TokenDTO tokens = tokenService.reissueTokens(refreshToken);
        return new ReissueTokenResponse(tokens.accessToken(), tokens.refreshToken());
    }

    public void logout(Member member) {
        tokenService.expireTokens(member.getPhone().getNumber());
    }

    public void withdrawMember(Member member) {
        memberService.withdraw(member);
        logout(member);
    }
}
