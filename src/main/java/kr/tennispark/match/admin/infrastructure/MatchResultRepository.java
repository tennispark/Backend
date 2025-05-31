package kr.tennispark.match.admin.infrastructure;

import kr.tennispark.match.common.domain.entity.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {
}
