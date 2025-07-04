package kr.tennispark.notification.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.notification.domain.entity.NotificationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationScheduleRepository extends JpaRepository<NotificationSchedule, Long> {

    List<NotificationSchedule> findByScheduledTimeBefore(LocalDateTime time);
}
