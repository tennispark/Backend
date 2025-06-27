package kr.tennispark.match.common.infrastructure.repository;

import java.util.List;

public interface MatchPointRankingRepository {
    void increaseScore(Long memberId, int delta);

    Long getRank(Long memberId);

    List<RankingEntry> findTop(int size);

    record RankingEntry(Long memberId, int score) {
    }
}
