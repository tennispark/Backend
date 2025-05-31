package kr.tennispark.point.user.infrastrurcture.repository;

import java.util.List;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.common.domain.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    List<PointHistory> findAllByMember(Member member);
}
