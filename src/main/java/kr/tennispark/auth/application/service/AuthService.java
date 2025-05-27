package kr.tennispark.auth.application.service;

import kr.tennispark.auth.application.JwtTokenProvider;
import kr.tennispark.auth.domain.vo.VerificationCode;
import kr.tennispark.auth.infrastructure.sms.SmsService;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.members.user.presentation.dto.response.LoginMemberResponse;
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

    public LoginMemberResponse login() {
        // 1. 핸드폰 인증번호로 인증 -> 인증 확인 처리 (redis)
        // 2. 핸드폰 번호로 member 객체 찾기
        Member member = memberRepository.getMemberByPhone_Number(request.phoneNumber());
        // 3. 토큰 발급
        return issueTokensFor(member);
    }

    private LoginMemberResponse issueTokensFor(Member member) {
        // 논의필요
        return LoginMemberResponse.of(
                jwtTokenProvider.createAccessToken(String.valueOf(member.getId()), "USER"),
                jwtTokenProvider.createRefreshToken(String.valueOf(member.getId()), "USER")
        );
    }
}
