package kr.tennispark.auth.admin.application.service;

import java.time.Duration;
import java.time.Instant;
import kr.tennispark.auth.common.application.JwtTokenProvider;
import kr.tennispark.common.infrastructure.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminRedisBlacklistService {

    private static final String BLACKLIST_PREFIX = "admin:blacklist:";
    private static final String BLACKLIST_VALUE = "blacklist";

    private final RedisRepository redis;
    private final JwtTokenProvider jwtTokenProvider;

    public boolean isBlacklisted(String refreshToken) {
        return redis.find(buildBlacklistKey(refreshToken)) != null;
    }

    public void saveBlacklist(String refreshToken) {
        Instant exp = jwtTokenProvider.getExpires(refreshToken);
        Duration ttl = Duration.between(Instant.now(), exp);
        if (!ttl.isNegative()) {
            redis.save(buildBlacklistKey(refreshToken), BLACKLIST_VALUE, ttl);
        }
    }

    private String buildBlacklistKey(String token) {
        return BLACKLIST_PREFIX + token;
    }
}

