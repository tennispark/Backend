package kr.tennispark.notification.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.notification.domain.entity.NotificationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationScheduleRepository extends JpaRepository<NotificationSchedule, Long> {

    @Query("""
                SELECT ns FROM NotificationSchedule ns
                JOIN FETCH ns.activity a
                WHERE ns.scheduledTime < :time
                AND ns.status = true
                AND a.status = true
            """)
    List<NotificationSchedule> findByScheduledTimeBeforeWithActivity(@Param("time") LocalDateTime time);
}
