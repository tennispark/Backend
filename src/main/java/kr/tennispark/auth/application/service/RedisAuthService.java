package kr.tennispark.auth.application.service;


import java.time.Duration;
import kr.tennispark.common.infrastructure.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisAuthService {
    private static final String CODE_PREFIX = "auth:code:";
    private static final String STATUS_PREFIX = "auth:status:";

    private static final Duration CODE_TTL = Duration.ofMinutes(5);
    private static final Duration STATUS_TTL = Duration.ofMinutes(10);

    private final RedisRepository redisRepository;

    public void saveCode(String phoneNumber, String code) {
        redisRepository.save(buildCodeKey(phoneNumber), code, CODE_TTL);
    }

    public String getCode(String phoneNumber) {
        return redisRepository.find(buildCodeKey(phoneNumber));
    }

    public void deleteCode(String phoneNumber) {
        redisRepository.delete(buildCodeKey(phoneNumber));
    }

    public void saveVerifiedStatus(String phoneNumber) {
        redisRepository.save(buildStatusKey(phoneNumber), "VERIFIED", STATUS_TTL);
    }

    public boolean isVerified(String phoneNumber) {
        return "VERIFIED".equals(redisRepository.find(buildStatusKey(phoneNumber)));
    }

    private String buildCodeKey(String phoneNumber) {
        return CODE_PREFIX + phoneNumber;
    }

    private String buildStatusKey(String phoneNumber) {
        return STATUS_PREFIX + phoneNumber;
    }
}
