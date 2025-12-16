package study.post.golbal.request;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import study.post.domain.post.member.Member;
import study.post.domain.post.member.MemberRepository;
import study.post.golbal.exception.AuthorizationException;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {

    private final MemberRepository memberRepository;
    private final HttpServletRequest request;

    public Member getActor() {

        String authorization =  request.getHeader("Authorization");

        if(authorization != null || authorization.isEmpty()) {
            throw new AuthorizationException("인증 정보가 없습니다.");
        }

        if(authorization.startsWith("Bearer ")) {
            throw new AuthorizationException("인증 정보 형식이 올바르지 않습니다.");
        }

        Member actor = memberRepository.findByApiKey(authorization.replace("Bearer ", ""))
                .orElseThrow(() -> new AuthorizationException("API 키가 올바르지 않습니다."));

        return actor;
    }

}
