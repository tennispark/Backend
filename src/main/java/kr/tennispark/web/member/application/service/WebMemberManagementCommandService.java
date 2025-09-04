package kr.tennispark.web.member.application.service;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.web.member.presentation.dto.UpdateCouponRequest;
import kr.tennispark.web.member.presentation.dto.UpdateMemberTypeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WebMemberManagementCommandService {

    private final MemberRepository memberRepository;

    public void updateCoupon(Long memberId, UpdateCouponRequest req) {
        Member member = memberRepository.getById(memberId);
        member.updateCouponValue(req.coupon());
    }

    public void updateMemberType(Long memberId, UpdateMemberTypeRequest req) {
        Member member = memberRepository.getById(memberId);
        member.updateMemberShipType(req.type());
    }
}
