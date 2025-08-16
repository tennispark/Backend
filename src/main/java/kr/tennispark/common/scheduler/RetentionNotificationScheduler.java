package kr.tennispark.common.scheduler;

import java.time.LocalDateTime;
import kr.tennispark.notification.admin.infrastructure.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetentionNotificationScheduler {

    private final NotificationRepository notificationRepository;

    @Value("${app.notification.retention-days:30}")
    private int retentionDays;

    @Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
    @Transactional
    public void purgeOldNotifications() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(retentionDays);

        int updated = notificationRepository.deleteOlderThan(cutoff);
        log.info("NotificationRetentionScheduler - soft-deleted {} notifications older than {}", updated, cutoff);
    }
}
