package kr.tennispark.activity.user.infrastructure.repository;

import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.enums.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends JpaRepository<Activity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Activity a where a.id = :id and a.status = true and a.type = 'GENERAL'")
    Optional<Activity> findForUpdate(@Param("id") Long id);

    @Query("select a from Activity a where a.date >= :date and a.type = :type and a.status = true")
    List<Activity> findAllGeneralActivitiesFrom(@Param("date") LocalDate date, @Param("type") ActivityType type);
}
