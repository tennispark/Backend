package kr.tennispark.notification.admin.infrastructure;

import java.time.LocalDateTime;
import kr.tennispark.notification.common.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE Notification n
                   SET n.status = false
                 WHERE n.status = true
                   AND n.createdAt < :cutoff
            """)
    int deleteOlderThan(@Param("cutoff") LocalDateTime cutoff);
}
