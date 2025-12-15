package study.post.domain.post.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthTokenService {

    private String secretPattern = "a4S7fKjP9xYmNc2Qe8hTzGvWb1uA5oD6lR0iE3pOyJcXrVbNqU9sO3gI0hL8kM7wPzYmXqRtVnBcA";
    private long expireSecond = 1000L * 60 * 60 * 24 * 365;


}
