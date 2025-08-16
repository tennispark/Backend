package kr.tennispark.activity.user.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import kr.tennispark.members.common.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {

    boolean existsByMemberAndActivityAndApplicationStatus(
            Member member, Activity activity, ApplicationStatus status);

    @Query("""
                    SELECT COUNT(DISTINCT aa.member.id) 
                    FROM ActivityApplication aa
                    WHERE aa.createdAt BETWEEN :start AND :end
                    AND aa.status = true
            """)
    int countDistinctMemberIdByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT aa
            FROM ActivityApplication aa
            WHERE aa.member = :member
              AND aa.status = true
              AND aa.applicationStatus IN ('PENDING','APPROVED', 'WAINTING')
            """)
    List<ActivityApplication> findActiveByMember(@Param("member") Member member);

    @Query(
            value = """
                        SELECT aa
                        FROM ActivityApplication aa
                        JOIN FETCH aa.activity a
                        JOIN FETCH a.template t
                        WHERE aa.member.id = :memberId
                          AND aa.status = true
                          AND a.status = true
                          AND t.status = true
                        ORDER BY aa.createdAt DESC
                    """,
            countQuery = """
                        SELECT COUNT(aa.id)
                        FROM ActivityApplication aa
                        JOIN aa.activity a
                        JOIN a.template t
                        WHERE aa.member.id = :memberId
                        AND aa.status = true
                        AND a.status = true
                        AND t.status = true
                    """
    )
    Page<ActivityApplication> findMyApplications(
            @Param("memberId") Long memberId,
            Pageable pageable
    );
}
