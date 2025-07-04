package kr.tennispark.common.scheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityRepository;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.notification.application.FcmMessageService;
import kr.tennispark.notification.application.NotificationMessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendRecruitReminderScheduler {

    private final FcmMessageService fcmMessageService;
    private final ActivityRepository activityRepository;

    @Scheduled(cron = "0 0 10 ? * MON")
    @Transactional(readOnly = true)
    public void sendRecruitReminder() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);

        List<Activity> activitiesWithVacancy = activityRepository.findThisWeeksVacantActivities(startOfWeek, endOfWeek);

        if (activitiesWithVacancy.isEmpty()) {
            return;
        }

        String message = NotificationMessageFactory.createRecruitReminderMessage(activitiesWithVacancy);

        fcmMessageService.sendBroadcastMessage(message);
    }
}
