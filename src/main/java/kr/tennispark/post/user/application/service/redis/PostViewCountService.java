package kr.tennispark.post.user.application.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostViewCountService {

    private static final String KEY_PREFIX = "post:view:";

    private final StringRedisTemplate redisTemplate;

    public long incrementAndGet(Long postId) {
        Long v = redisTemplate.opsForValue().increment(key(postId), 1L);
        return v != null ? v : 0L;
    }

    public long get(Long postId) {
        String s = redisTemplate.opsForValue().get(key(postId));
        if (s == null) {
            return 0L;
        }
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    private String key(Long id) {
        return KEY_PREFIX + id;
    }
}
