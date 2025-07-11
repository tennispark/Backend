package kr.tennispark.point.user.infrastrurcture.repository;

import java.util.Optional;
import kr.tennispark.point.common.domain.entity.Point;
import kr.tennispark.members.common.domain.exception.NoSuchMemberException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    Optional<Point> findByMember_Id(Long memberId);

    default Point getByMemberId(Long memberId) {
        return findByMember_Id(memberId)
                .orElseThrow(NoSuchMemberException::new);
    }
}
