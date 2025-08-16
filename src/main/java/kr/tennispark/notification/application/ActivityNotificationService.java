package kr.tennispark.notification.application;

import io.micrometer.common.util.StringUtils;
import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.notification.domain.entity.NotificationSchedule;
import kr.tennispark.notification.domain.entity.enums.NotificationCategory;
import kr.tennispark.notification.domain.entity.enums.NotificationType;
import kr.tennispark.notification.infrastructure.NotificationScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityNotificationService {

    private static final String MESSAGE_CONTENT = "신청하신 활동이 확정되었습니다.";
    private static final Integer ONE_DAY = 1;
    private static final Integer ONE_HOUR = 1;

    private final NotificationPublisher publisher;
    private final NotificationScheduleRepository notificationScheduleRepository;

    @Transactional
    public void notifyApprovedApplication(ActivityApplication application) {
        String fcmToken = application.getMember().getFcmToken();
        if (StringUtils.isBlank(fcmToken)) {
            return;
        }

        // 신청 상태 승인일 때만, 승인상태에서 다른 상태로 변경되면 future 알림 삭제해야함
        publisher.notifyMembers(List.of(application.getMember()), NotificationCategory.ACTIVITY_GUIDE, MESSAGE_CONTENT);
        createFutureNotification(application, application.getMember());
    }

    private void createFutureNotification(ActivityApplication application, Member member) {
        LocalDateTime beginAt = application.getActivity().getDate()
                .atTime(application.getActivity().getScheduledTime().getBeginAt());

        NotificationSchedule oneDayBefore = createNotificationSchedule(
                application, NotificationType.ONE_DAY_BEFORE, beginAt.minusDays(ONE_DAY), member);

        NotificationSchedule oneHourBefore = createNotificationSchedule(
                application, NotificationType.ONE_HOUR_BEFORE, beginAt.minusHours(ONE_HOUR), member);

        notificationScheduleRepository.saveAll(List.of(oneDayBefore, oneHourBefore));
    }

    private NotificationSchedule createNotificationSchedule(
            ActivityApplication application,
            NotificationType type,
            LocalDateTime scheduledTime,
            Member member
    ) {
        return NotificationSchedule.of(
                application.getActivity(),
                type,
                scheduledTime,
                member.getFcmToken(),
                member
        );
    }
}
