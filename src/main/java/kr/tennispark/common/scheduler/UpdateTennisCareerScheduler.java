package kr.tennispark.common.scheduler;

import kr.tennispark.members.admin.application.TennisCareerMonthlyUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateTennisCareerScheduler {

    private final TennisCareerMonthlyUpdater updater;

    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul")
    public void run() {
        updater.updateTennisCareer();
    }
}
