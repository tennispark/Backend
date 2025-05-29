package kr.tennispark.record.admin.infrastructure;

import kr.tennispark.record.common.domain.entity.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {
}
