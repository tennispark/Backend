package kr.tennispark.match.common.application.service;

import kr.tennispark.match.common.application.dto.MatchPointIncreasedEvent;
import kr.tennispark.match.common.infrastructure.repository.MatchPointRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MatchPointRankingEventHandler {

    private final MatchPointRankingRepository rankingRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(MatchPointIncreasedEvent event) {
        rankingRepository.increaseScore(event.memberId(), event.point());
    }
}
