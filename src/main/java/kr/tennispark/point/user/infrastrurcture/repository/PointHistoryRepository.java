package kr.tennispark.point.user.infrastrurcture.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.point.common.domain.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Query("""
            SELECT COALESCE(SUM(ph.amount), 0) 
            FROM PointHistory ph 
            WHERE ph.createdAt BETWEEN :start AND :end 
            AND ph.amount > 0 
            AND ph.status = true 
            """)
    int sumAmountByCreatedAtBetweenAndAmountGreaterThan(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT ph.point.member 
            FROM PointHistory ph
            WHERE ph.createdAt BETWEEN :start AND :end 
            AND ph.amount > 0 
            AND ph.status = true 
            GROUP BY ph.point.member 
            ORDER BY SUM(ph.amount) DESC
            LIMIT 1
            """)
    Optional<Member> findTopEarner(LocalDateTime start, LocalDateTime end);

    @Query(""" 
            SELECT ph.point.member 
            FROM PointHistory ph 
            WHERE ph.createdAt BETWEEN :start AND :end 
            AND ph.amount < 0 
            AND ph.status = true 
            GROUP BY ph.point.member 
            ORDER BY SUM(ph.amount) ASC 
            LIMIT 1
            """)
    Optional<Member> findTopSpender(LocalDateTime start, LocalDateTime end);

    List<PointHistory> findAllByMember(Member member);
}
