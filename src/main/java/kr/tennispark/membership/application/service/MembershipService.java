package kr.tennispark.membership.application.service;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.membership.application.exception.MembershipAlreadyExistsException;
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

    private final MembershipRepository membershipRepository;

    @Transactional
    public void registerMembership(Member member, RegisterMembershipRequest request) {
        if (membershipRepository.existsByMember(member)) {
            throw new MembershipAlreadyExistsException();
        }

        Membership membership = Membership.of(
                member,
                request.recommender(),
                request.reason(),
                request.membershipType(),
                request.courtType(),
                request.period()
        );

        membershipRepository.save(membership);
    }
}
