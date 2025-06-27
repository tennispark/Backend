package kr.tennispark.common.scheduler;

import kr.tennispark.match.common.infrastructure.repository.impl.MatchPointRankingSynchronizer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SyncMatchPointRankingScheduler {

    private final MatchPointRankingSynchronizer synchronizer;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void run() {
        synchronizer.sync();
    }
}
