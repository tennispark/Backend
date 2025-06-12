package kr.tennispark.match.common.infrastructure.repository;

public interface MatchPointRankingRepository {
    void increaseScore(Long memberId, int delta);

    Long getRank(Long memberId);
}
