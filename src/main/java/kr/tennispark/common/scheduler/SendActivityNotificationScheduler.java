package kr.tennispark.common.scheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.tennispark.activity.admin.infrastructure.repository.AdminActivityApplicationRepository;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.notification.admin.application.NotificationMessageFactory;
import kr.tennispark.notification.admin.application.NotificationPublisher;
import kr.tennispark.notification.admin.infrastructure.NotificationScheduleRepository;
import kr.tennispark.notification.common.domain.entity.NotificationSchedule;
import kr.tennispark.notification.common.domain.entity.enums.NotificationCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendActivityNotificationScheduler {

    private final NotificationPublisher publisher;
    private final NotificationScheduleRepository notificationScheduleRepository;
    private final AdminActivityApplicationRepository adminActivityApplicationRepository;

    @Scheduled(cron = "0 */30 * * * *")
    @Transactional
    public void sendScheduledNotifications() {
        LocalDateTime now = LocalDateTime.now();
        List<NotificationSchedule> schedules = notificationScheduleRepository.findByScheduledTimeBeforeWithActivity(
                now);

        List<Long> fetchedIds = schedules.stream().map(NotificationSchedule::getId).toList();
        log.info("[스케줄 로그] fetched count={}, ids={}", Integer.valueOf(fetchedIds.size()), fetchedIds);

        if (schedules.isEmpty()) {
            log.info("[스케줄 로그] 스케줄 없음");
            return;
        }

        Map<String, List<Member>> messageToTokens = new HashMap<>();
        List<Long> schedulesToDelete = new ArrayList<>();

        for (NotificationSchedule schedule : schedules) {
            try {
                Activity activity = schedule.getActivity();

                List<String> participantNames = adminActivityApplicationRepository.findAllByActivity(activity)
                        .stream()
                        .filter(app -> app.getApplicationStatus().isAccepted())
                        .filter(app -> !app.isDeleted() && !app.getMember().isDeleted())
                        .map(app -> app.getMember().getName())
                        .toList();

                String message = NotificationMessageFactory.reminderMessage(
                        schedule.getNotificationType(),
                        activity,
                        participantNames
                );
                log.info("[스케줄 로그] message length({})", message.length());
                messageToTokens.computeIfAbsent(message, k -> new ArrayList<>())
                        .add(schedule.getMember());

                schedulesToDelete.add(schedule.getId());

            } catch (Exception e) {
                log.error("[스케줄 로그] 알림 처리 중 예외 발생: scheduleId={}", schedule.getId(), e);
            }
        }

        // 그룹핑된 메시지별 FCM 전송
        for (Map.Entry<String, List<Member>> entry : messageToTokens.entrySet()) {
            List<Member> members = entry.getValue();
            publisher.notifyMembers(members, NotificationCategory.MATCHING_GUIDE, entry.getKey());
        }

        if (!schedulesToDelete.isEmpty()) {
            log.info("[스케줄 로그] softDelete request count={}, ids={}",
                    Integer.valueOf(schedulesToDelete.size()), schedulesToDelete);

            int updated = notificationScheduleRepository.softDeleteByIds(schedulesToDelete);
            log.info("[스케줄 로그] softDelete updated={}", Integer.valueOf(updated));

            // 업데이트 되지 않은(여전히 true인) 아이디 점검
            List<NotificationSchedule> stillTrue = notificationScheduleRepository.findAllById(schedulesToDelete);
            if (!stillTrue.isEmpty()) {
                List<Long> remainingIds = stillTrue.stream().map(NotificationSchedule::getId).toList();
                log.warn("[스케줄 로그]still TRUE after softDelete: count={}, ids={}",
                        Integer.valueOf(remainingIds.size()), remainingIds);
            } else {
                log.info("[스케줄 로그] verification: no remaining TRUE schedules");
            }
        } else {
            log.info("[스케줄 로그] nothing to softDelete");
        }
    }
}
