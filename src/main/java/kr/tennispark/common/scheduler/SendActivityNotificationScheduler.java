package kr.tennispark.common.scheduler;

import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.activity.admin.infrastructure.repository.AdminActivityApplicationRepository;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.members.common.domain.entity.Member;
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

        for (NotificationSchedule schedule : schedules) {
            try {
                Activity activity = schedule.getActivity();

                List<String> participantNames = adminActivityApplicationRepository.findAllByActivity(activity)
                        .stream().map(ActivityApplication::getMember)
                        .map(Member::getName)
                        .toList();

                String message = NotificationMessageFactory.createMessage(
                        schedule.getNotificationType(),
                        activity,
                        participantNames
                );

                fcmMessageService.sendMessage(List.of(schedule.getTargetTokens()), message);
                notificationScheduleRepository.delete(schedule);

            } catch (Exception e) {
                log.error("알림 전송 실패: scheduleId={}", schedule.getId(), e);
            }
        }
    }
}