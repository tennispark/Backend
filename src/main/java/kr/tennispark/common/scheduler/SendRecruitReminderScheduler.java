package kr.tennispark.common.scheduler;

import java.time.LocalDate;
import java.util.List;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityRepository;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.vo.WeekPeriod;
import kr.tennispark.notification.admin.application.NotificationMessageFactory;
import kr.tennispark.notification.admin.application.NotificationPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendRecruitReminderScheduler {

    private static final Integer DAYS_TO_ADD = 6;

    private final NotificationPublisher publisher;
    private final ActivityRepository activityRepository;

    @Scheduled(cron = "0 30 8 ? * MON-THU")
    @Transactional
    public void sendRecruitReminder() {
        WeekPeriod period = WeekPeriod.thisWeek();

        List<Activity> activitiesWithVacancy = activityRepository.findThisWeeksVacantActivities(
                LocalDate.now(), period.end()
        );

        if (activitiesWithVacancy.isEmpty()) {
            return;
        }

        String message = NotificationMessageFactory.recruitReminderMessage(activitiesWithVacancy);
        publisher.broadcast(message);
    }
}
