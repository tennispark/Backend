package kr.tennispark.auth.user.application.service;


import java.time.Duration;
import kr.tennispark.auth.common.application.exception.ExpiredTokenException;
import kr.tennispark.common.infrastructure.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTokenService {

    private static final String REFRESH_PREFIX = "refresh:";

    private final RedisRepository redisRepository;

    @Value("${security.jwt.token.refresh.expire-length}")
    private long refreshTokenExpireMillis;
    @Value("${security.jwt.token.access.expire-length}")
    private long accessTokenExpireMillis;

    public void saveRefreshToken(String phoneNumber, String refreshToken) {
        redisRepository.save(buildRefreshKey(phoneNumber), refreshToken, Duration.ofMillis(refreshTokenExpireMillis));
    }

    public void validateRefreshToken(String phoneNumber, String refreshToken) {
        String existedToken = redisRepository.find(buildRefreshKey(phoneNumber));
        if (!refreshToken.equals(existedToken)) {
            throw new ExpiredTokenException();
        }
    }

    public void deleteRefreshToken(String phoneNumber) {
        redisRepository.delete(buildRefreshKey(phoneNumber));
    }

    private String buildRefreshKey(String value) {
        return REFRESH_PREFIX + value;
    }
}
