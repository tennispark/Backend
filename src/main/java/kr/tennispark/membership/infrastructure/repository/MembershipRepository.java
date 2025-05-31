package kr.tennispark.membership.infrastructure.repository;

import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.membership.common.domain.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    boolean existsByMember(Member member);
}
