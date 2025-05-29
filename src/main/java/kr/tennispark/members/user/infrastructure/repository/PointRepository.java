package kr.tennispark.members.user.infrastructure.repository;

import java.util.Optional;
import kr.tennispark.members.common.domain.entity.vo.Point;
import kr.tennispark.members.common.domain.exception.NoSuchMemberException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

    Optional<Point> findByMember_Id(Long memberId);

    default Point getByMemberId(Long memberId) {
        return findByMember_Id(memberId)
                .orElseThrow(NoSuchMemberException::new);
    }
}
