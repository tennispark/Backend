package kr.tennispark.match.admin.infrastructure;

import kr.tennispark.match.common.domain.entity.association.MatchParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRecordRepository extends JpaRepository<MatchParticipation, Long> {
}
