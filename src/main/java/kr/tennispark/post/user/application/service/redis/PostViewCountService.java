package kr.tennispark.post.user.application.service.redis;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Map<Long, Long> getBulk(Collection<Long> postIds) {
        if (postIds == null || postIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<String> keys = postIds.stream().map(this::key).toList();
        List<String> values = redisTemplate.opsForValue().multiGet(keys);
        Map<Long, Long> result = new HashMap<>();
        int i = 0;
        for (Long id : postIds) {
            String v = (values != null && i < values.size()) ? values.get(i) : null;
            long n = 0L;
            if (v != null) {
                try {
                    n = Long.parseLong(v);
                } catch (NumberFormatException ignore) {
                }
            }
            result.put(id, n);
            i++;
        }
        return result;
    }

    private String key(Long id) {
        return KEY_PREFIX + id;
    }
}
