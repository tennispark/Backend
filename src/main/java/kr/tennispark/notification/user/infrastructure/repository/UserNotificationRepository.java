package kr.tennispark.notification.user.infrastructure.repository;

import java.util.List;
import kr.tennispark.notification.common.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
