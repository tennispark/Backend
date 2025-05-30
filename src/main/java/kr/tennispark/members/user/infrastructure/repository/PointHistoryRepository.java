package kr.tennispark.members.user.infrastructure.repository;

import kr.tennispark.members.common.domain.entity.association.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
}
