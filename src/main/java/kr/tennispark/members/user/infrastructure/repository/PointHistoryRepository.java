package kr.tennispark.members.user.infrastructure.repository;

import kr.tennispark.point.common.domain.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
}
