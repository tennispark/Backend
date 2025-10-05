package kr.tennispark.membership.application.service;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.enums.MemberShipType;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import kr.tennispark.membership.common.domain.entity.Membership;
import kr.tennispark.membership.infrastructure.repository.MembershipRepository;
import kr.tennispark.membership.user.presentation.dto.request.RegisterMembershipRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipService {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    @Transactional
    public void registerMembership(Member member, RegisterMembershipRequest request) {
        Membership membership = membershipRepository.findByMember(member)
                .map(m -> {
                    m.update(request.recommender(), request.reason(), request.courtType(),
                            request.period());
                    return m;
                })
                .orElseGet(() -> Membership.of(
                        member,
                        request.recommender(),
                        request.reason(),
                        request.membershipType(),
                        request.courtType(),
                        request.period()
                ));
        member.updateMemberShipType(MemberShipType.MEMBERSHIP);

        memberRepository.save(member);
        membershipRepository.save(membership);
    }
}
