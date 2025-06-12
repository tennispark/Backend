package kr.tennispark.match.common.infrastructure.repository.impl;

import kr.tennispark.match.common.infrastructure.repository.MatchPointRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchPointRankingRedisRepository implements MatchPointRankingRepository {

    private static final String KEY = "ranking:member:matchPoint";
    private final StringRedisTemplate redis;

    @Override
    public void increaseScore(Long memberId, int delta) {
        redis.opsForZSet().incrementScore(KEY, memberId.toString(), delta);
    }

    @Override
    public Long getRank(Long memberId) {
        Long rank = redis.opsForZSet().reverseRank(KEY, memberId.toString());
        return rank == null ? null : rank + 1;
    }
}
