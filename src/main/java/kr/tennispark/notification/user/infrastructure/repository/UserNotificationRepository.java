package kr.tennispark.notification.user.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.notification.common.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserNotificationRepository extends JpaRepository<Notification, Long> {

    @Query("""
                SELECT n
                  FROM Notification n
                 WHERE n.status = true
                   AND n.member.id = :memberId
                 ORDER BY n.createdAt DESC, n.id DESC
            """)
    List<Notification> findAllByMemberIdOrderByLatest(@Param("memberId") Long memberId);
    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
                UPDATE notification
                   SET read_at = :now, updated_at = :now
                 WHERE member_id = :memberId
                   AND status = true
                   AND read_at IS NULL
            """, nativeQuery = true)
    int markAllUnreadAsRead(@Param("memberId") Long memberId, @Param("now") LocalDateTime now);
}
