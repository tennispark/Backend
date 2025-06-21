package kr.tennispark.common.scheduler;

import kr.tennispark.activity.admin.application.impl.WeeklyActivityGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeeklyActivityScheduler {

    private final WeeklyActivityGenerator generator;

    @Scheduled(cron = "0 5 0 * * FRI", zone = "Asia/Seoul")
    public void run() {
        generator.generateCurrentWeek();
    }
}
