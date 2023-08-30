package vttp.backend.repository;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepo {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, String> rTemplate;

    public void savePosting(String postingId, String posting) throws IllegalArgumentException {
        rTemplate.opsForValue()
                .set(postingId, posting, Duration.ofMinutes(15));
    }

    public Optional<String> getPosting(String postingId) {
        return Optional.ofNullable(
                rTemplate.opsForValue()
                        .get(postingId));
    }

    public void deletePosting(String postingId) {
        rTemplate.opsForValue().getAndDelete(postingId);
    }
}
