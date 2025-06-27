package kr.tennispark.match.common.infrastructure.repository.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import kr.tennispark.members.common.domain.entity.Member;
import kr.tennispark.members.user.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchPointRankingSynchronizer {

    private static final String KEY = "ranking:member:matchPoint";

    private final StringRedisTemplate redis;
    private final MemberRepository memberRepository;

    public void sync() {
        List<Member> members = memberRepository.findAll();
        if (members.isEmpty()) {
            return;
        }

        Set<ZSetOperations.TypedTuple<String>> tuples = members.stream()
                .map(m -> new DefaultTypedTuple<>(
                        m.getId().toString(),
                        m.getMatchPoint().doubleValue()
                ))
                .collect(Collectors.toSet());

        redis.opsForZSet().add(KEY, tuples);
    }
}