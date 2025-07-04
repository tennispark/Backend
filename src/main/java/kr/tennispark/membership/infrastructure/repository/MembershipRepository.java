package kr.tennispark.membership.infrastructure.repository;

import java.util.Optional;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.membership.common.domain.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    boolean existsByMember(Member member);

    Optional<Membership> findByMember(Member member);
}
