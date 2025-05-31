package kr.tennispark.activity.admin.infrastructure.repository;

import java.time.LocalDate;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    boolean existsByTemplateAndDate(ActivityInfo template, LocalDate date);

    Page<Activity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
