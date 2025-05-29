package kr.tennispark.members.user.infrastructure.repository;

import kr.tennispark.members.common.domain.entity.vo.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
}
