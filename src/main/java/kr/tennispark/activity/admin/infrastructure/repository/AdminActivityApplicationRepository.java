package kr.tennispark.activity.admin.infrastructure.repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import kr.tennispark.activity.admin.application.exception.NoSuchActivityApplicationException;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.activity.common.domain.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {

    @Query("""
                SELECT aa
                FROM ActivityApplication aa
                JOIN FETCH aa.member m
                WHERE aa.activity = :activity
                AND m.status = true
                ORDER BY aa.createdAt ASC
            """)
    Page<ActivityApplication> findAllValidByActivity(Activity activity, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
                        SELECT aa
                        FROM ActivityApplication aa
                        JOIN FETCH aa.member m
                        JOIN FETCH aa.activity a
                        WHERE m.id = :memberId AND a.id = :activityId
                        AND aa.status = true
                        AND m.status = true
                        AND a.status = true
            """)
    Optional<ActivityApplication> findByMemberIdAndActivityId(Long memberId, Long activityId);

    default ActivityApplication getByMemberIdAndActivityId(Long memberId, Long activityId) {
        return findByMemberIdAndActivityId(memberId, activityId)
                .orElseThrow(NoSuchActivityApplicationException::new);
    }

    List<ActivityApplication> findAllByActivity(Activity activity);

    void deleteAllByActivity(Activity activity);

    @Query("""
            SELECT aa.activity.id AS activityId, COUNT(aa) AS cnt
            FROM ActivityApplication aa
            WHERE aa.status = true
              AND aa.applicationStatus = :status
              AND aa.activity.id IN :activityIds
            GROUP BY aa.activity.id
            """)
    List<PendingCountRow> countPendingByActivityIds(
            @Param("activityIds") List<Long> activityIds,
            @Param("status") ApplicationStatus status
    );

    interface PendingCountRow {
        Long getActivityId();

        long getCnt();
    }
}
