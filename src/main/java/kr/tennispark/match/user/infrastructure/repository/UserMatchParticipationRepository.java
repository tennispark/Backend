package kr.tennispark.match.user.infrastructure.repository;

import kr.tennispark.match.common.domain.entity.association.MatchParticipation;
import kr.tennispark.match.common.domain.entity.enums.MatchOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMatchParticipationRepository extends JpaRepository<MatchParticipation, Long> {

    long countByMemberIdAndMatchOutcome(Long memberId, MatchOutcome outcome);
}
