package kr.tennispark.activity.admin.infrastructure.repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import kr.tennispark.activity.admin.application.exception.NoSuchActivityApplicationException;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface AdminActivityApplicationRepository extends JpaRepository<ActivityApplication, Long> {

    @Query("""
                SELECT aa
                FROM ActivityApplication aa
                JOIN FETCH aa.member m
                WHERE aa.activity = :activity
                AND m.status = true
                ORDER BY aa.createdAt DESC
            """)
    Page<ActivityApplication> findAllValidByActivity(Activity activity, Pageable pageable);

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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    default ActivityApplication getByMemberIdAndActivityId(Long memberId, Long activityId) {
        return findByMemberIdAndActivityId(memberId, activityId)
                .orElseThrow(NoSuchActivityApplicationException::new);
    }

    List<ActivityApplication> findAllByActivity(Activity activity);
}
