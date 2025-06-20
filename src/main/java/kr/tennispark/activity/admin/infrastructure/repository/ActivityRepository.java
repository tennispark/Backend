package kr.tennispark.activity.admin.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityInfo;
import kr.tennispark.activity.common.domain.exception.NoSuchActivityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
