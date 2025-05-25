//package kr.tennispark.common.aop;
//
//
//import lombok.RequiredArgsConstructor;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class AdminCheckAspect {
//
//    private final MemberRepository memberRepository;
//
//    @Before("@annotation(com.prography.lighton.common.annotation.AdminOnly)")
//    public void checkAdminAuthority() {
//        Long memberId = SecurityUtils.getCurrentMemberId();
//
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new UnauthorizedException("회원 정보가 존재하지 않습니다."));
//
//        if (!member.isAdmin()) {
//            throw new ForbiddenException("관리자 권한이 없습니다.");
//        }
//    }
//}
