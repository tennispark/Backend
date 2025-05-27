package kr.tennispark.auth.application.service;


import java.time.Duration;
import kr.tennispark.common.infrastructure.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisAuthService {

    private static final String PREFIX = "auth:code:";
    private static final Duration TTL = Duration.ofMinutes(5);

    private final RedisRepository redisRepository;

    public void saveCode(String phoneNumber, String code) {
        String key = buildKey(phoneNumber);
        redisRepository.save(key, code, TTL);
    }

    public String getCode(String phoneNumber) {
        String key = buildKey(phoneNumber);
        return redisRepository.find(key);
    }

    public void deleteCode(String phoneNumber) {
        String key = buildKey(phoneNumber);
        redisRepository.delete(key);
    }

    private String buildKey(String phoneNumber) {
        return PREFIX + phoneNumber;
    }
}
