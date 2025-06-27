package kr.tennispark.match.common.infrastructure.repository.impl;

import java.util.List;
import java.util.Set;
import kr.tennispark.match.common.infrastructure.repository.MatchPointRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
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
        String memberKey = memberId.toString();
        ZSetOperations<String, String> zSet = redis.opsForZSet();

        Long rank = zSet.reverseRank(KEY, memberKey);
        if (rank == null) {
            zSet.add(KEY, memberKey, 0.0);
            rank = zSet.reverseRank(KEY, memberKey);

            if (rank == null) {
                throw new IllegalStateException("Redis에 순위 추가 후에도 조회되지 않습니다.");
            }
        }

        return rank + 1;
    }

    @Override
    public List<RankingEntry> findTop(int size) {
        Set<TypedTuple<String>> tuples =
                redis.opsForZSet().reverseRangeWithScores(KEY, 0, size - 1);

        if (tuples == null) {
            return List.of();
        }

        return tuples.stream()
                .map(t -> new RankingEntry(Long.valueOf(t.getValue()), t.getScore().intValue()))
                .toList();
    }
}
