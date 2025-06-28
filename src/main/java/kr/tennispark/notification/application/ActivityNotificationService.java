package kr.tennispark.notification.application;

import java.time.LocalDateTime;
import java.util.List;
import kr.tennispark.activity.common.domain.ActivityApplication;
import kr.tennispark.notification.domain.entity.NotificationSchedule;
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

    private final FcmMessageService messageService;
    private final NotificationScheduleRepository notificationScheduleRepository;

    @Transactional
    public void notifyApprovedApplication(ActivityApplication application) {
        messageService.sendMessage(List.of(application.getMember().getFcmToken()), MESSAGE_CONTENT);

        LocalDateTime beginAt = LocalDateTime.from(
                application.getActivity().getScheduledTime().getBeginAt());

        NotificationSchedule oneDayBefore = NotificationSchedule.of(
                application.getActivity(),
                NotificationType.ONE_DAY_BEFORE,
                beginAt.minusDays(ONE_DAY),
                application.getMember().getFcmToken()
        );

        NotificationSchedule oneHourBefore = NotificationSchedule.of(
                application.getActivity(),
                NotificationType.ONE_HOUR_BEFORE,
                beginAt.minusHours(ONE_HOUR),
                application.getMember().getFcmToken()
        );

        notificationScheduleRepository.saveAll(List.of(oneDayBefore, oneHourBefore));
    }
}