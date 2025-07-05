package kr.tennispark.common.scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.tennispark.activity.admin.infrastructure.repository.AdminActivityApplicationRepository;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.notification.application.FcmMessageService;
import kr.tennispark.notification.application.NotificationMessageFactory;
import kr.tennispark.notification.domain.entity.NotificationSchedule;
import kr.tennispark.notification.infrastructure.NotificationScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendActivityNotificationScheduler {

    private final FcmMessageService fcmMessageService;
    private final NotificationScheduleRepository notificationScheduleRepository;
    private final AdminActivityApplicationRepository adminActivityApplicationRepository;

    @Scheduled(cron = "0 */30 * * * *")
    @Transactional
    public void sendScheduledNotifications() {
        LocalDateTime now = LocalDateTime.now();
        List<NotificationSchedule> schedules = notificationScheduleRepository.findByScheduledTimeBefore(now);

        if (schedules.isEmpty()) {
            return;
        }

        Map<String, List<String>> messageToTokens = new HashMap<>();
        List<NotificationSchedule> schedulesToDelete = new ArrayList<>();

        for (NotificationSchedule schedule : schedules) {
            try {
                Activity activity = schedule.getActivity();

                List<String> participantNames = adminActivityApplicationRepository.findAllByActivity(activity)
                        .stream()
                        .map(app -> app.getMember().getName())
                        .toList();

                String message = NotificationMessageFactory.createMessage(
                        schedule.getNotificationType(),
                        activity,
                        participantNames
                );

                messageToTokens.computeIfAbsent(message, k -> new ArrayList<>())
                        .add(schedule.getTargetToken());

                schedulesToDelete.add(schedule);

            } catch (Exception e) {
                log.error("알림 처리 중 예외 발생: scheduleId={}", schedule.getId(), e);
            }
        }

        // 그룹핑된 메시지별 FCM 전송
        for (Map.Entry<String, List<String>> entry : messageToTokens.entrySet()) {
            List<String> tokens = entry.getValue();
            fcmMessageService.sendMessage(tokens, entry.getKey());
        }

        notificationScheduleRepository.deleteAll(schedulesToDelete);
    }
}
