package kr.tennispark.record.admin.infrastructure;

import kr.tennispark.record.common.domain.entity.association.MatchParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRecordRepository extends JpaRepository<MatchParticipation, Long> {
}
