package kr.tennispark.web.activity.infrastructure.repository;

import java.time.LocalDate;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebAdminActivityRepository extends JpaRepository<Activity, Long> {

    @Query("""
            SELECT a
            FROM Activity a
            WHERE a.date >= :fromDate
              AND a.status = true
            ORDER BY a.date ASC, a.scheduledTime.beginAt ASC, a.id ASC
            """)
    List<Activity> findFromDate(@Param("fromDate") LocalDate fromDate);

}
