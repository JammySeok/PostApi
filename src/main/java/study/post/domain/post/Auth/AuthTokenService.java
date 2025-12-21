package study.post.domain.post.Auth;

import org.springframework.stereotype.Service;
import study.post.domain.post.member.Member;
import study.post.standard.util.Ut;

import java.util.Map;

@Service
public class AuthTokenService {

    private String secretPattern = "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890";
    private long expireSeconds = 1000L * 60 * 60 * 24 * 365;

    public String genAccessToken(Member member) {

        return Ut.jwt.toString(
                secretPattern,
                expireSeconds,
                Map.of("id", member.getId(), "userid", member.getUserid())
        );
    }

    public Map<String, Object> payloadOrNull(String jwt) {
        Map<String, Object> payload = Ut.jwt.toPayload(jwt, secretPattern);

        if(payload == null) {
            return null;
        }

        int id = (int)payload.get("id");
        String userid = (String)payload.get("userid");

        // 필요한 정보만 뽑아서 return
        return Map.of("id", id, "userid", userid);
    }
}
