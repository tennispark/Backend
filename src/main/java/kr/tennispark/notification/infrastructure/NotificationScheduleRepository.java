package kr.tennispark.notification.infrastructure;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.notification.domain.entity.NotificationSchedule;
import kr.tennispark.notification.domain.entity.enums.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE NotificationSchedule ns
                   SET ns.status = false
                 WHERE ns.status = true
                   AND ns.activity = :activity
                   AND ns.member   = :member
                   AND ns.notificationType IN :types
            """)
    int deleteByActivityAndMemberAndTypes(
            @Param("activity") Activity activity,
            @Param("member") Member member,
            @Param("types") Collection<NotificationType> types
    );
}
