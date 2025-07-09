package kr.tennispark.activity.admin.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityInfo;
import kr.tennispark.activity.common.domain.exception.NoSuchActivityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    boolean existsByTemplateAndDate(ActivityInfo template, LocalDate date);

    List<Activity> findAllByTemplateAndDateAfter(ActivityInfo template, LocalDate date);

    long count();

    Page<Activity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    default Activity getById(Long activityId) {
        return findById(activityId)
                .orElseThrow(NoSuchActivityException::new);
    }

    @Query("""
                SELECT a FROM Activity a
                WHERE a.date BETWEEN :startOfWeek AND :endOfWeek
                  AND a.capacity > a.participantCount
                  AND a.type = 'GENERAL'
                  AND a.status = true
            """)
    List<Activity> findThisWeeksVacantActivities(
            @Param("startOfWeek") LocalDate startOfWeek,
            @Param("endOfWeek") LocalDate endOfWeek
    );
}
