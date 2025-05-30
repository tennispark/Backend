package kr.tennispark.members.user.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.common.domain.entity.association.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Query("""
            SELECT COALESCE(SUM(ph.amount), 0) 
            FROM PointHistory ph 
            WHERE ph.createdAt BETWEEN :start AND :end 
            AND ph.amount > 0
            """)
    int sumAmountByCreatedAtBetweenAndAmountGreaterThan(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT ph.point.member 
            FROM PointHistory ph
            WHERE ph.createdAt BETWEEN :start AND :end 
            AND ph.amount > 0 
            GROUP BY ph.point.member 
            ORDER BY SUM(ph.amount) DESC
            """)
    Optional<Member> findTopEarner(LocalDateTime start, LocalDateTime end);

    @Query(""" 
            SELECT ph.point.member 
            FROM PointHistory ph 
            WHERE ph.createdAt BETWEEN :start AND :end 
            AND ph.amount < 0 
            GROUP BY ph.point.member 
            ORDER BY SUM(ph.amount) ASC
            """)
    Optional<Member> findTopSpender(LocalDateTime start, LocalDateTime end);
}
