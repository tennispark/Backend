package kr.tennispark.common.scheduler;

import java.util.List;
import kr.tennispark.activity.admin.infrastructure.repository.ActivityRepository;
import kr.tennispark.activity.common.domain.Activity;
import kr.tennispark.activity.common.domain.vo.WeekPeriod;
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

    private final Integer DAYS_TO_ADD = 6;
    private final FcmMessageService fcmMessageService;
    private final ActivityRepository activityRepository;

    @Scheduled(cron = "0 0 10 ? * MON") // 매주 월요일 오전 10시
    @Transactional(readOnly = true)
    public void sendRecruitReminder() {
        WeekPeriod period = WeekPeriod.current();

        List<Activity> activitiesWithVacancy = activityRepository.findThisWeeksVacantActivities(
                period.start(), period.start().plusDays(DAYS_TO_ADD)
        );

        if (activitiesWithVacancy.isEmpty()) {
            return;
        }

        String message = NotificationMessageFactory.createRecruitReminderMessage(activitiesWithVacancy);
        fcmMessageService.sendBroadcastMessage(message);
    }
}
