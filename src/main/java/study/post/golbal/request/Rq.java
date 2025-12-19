package study.post.golbal.request;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import study.post.domain.post.member.Member;
import study.post.domain.post.member.MemberDto;
import study.post.domain.post.member.MemberMapper;
import study.post.domain.post.member.MemberRepository;
import study.post.golbal.exception.AuthorizationException;

@RequestScope  // 없어도 됨
@Component
@RequiredArgsConstructor
public class Rq {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    // HttpServlet이 RequestScope 이기 때문에
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public MemberDto getActor() {

        String apiKey = "";
        String authorization =  request.getHeader("Authorization");

        if(authorization != null && !authorization.isEmpty()) {
            if(!authorization.startsWith("Bearer ")) {
                throw new AuthorizationException("헤더의 인증 정보 형식이 올바르지 않습니다.");
            }

            apiKey = authorization.replace("Bearer ", "");
        }
        else {
            Cookie[] cookies = request.getCookies();

            if(cookies == null) {
                throw new AuthorizationException("인증 정보가 없습니다.");
            }

            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("apiKey")) {
                    apiKey = cookie.getValue();
                    break;
                }
            }
        }

        Member actor = memberRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new AuthorizationException("API 키가 올바르지 않습니다."));

        return memberMapper.toDto(actor);
    }

    public void addCookie(String name, String value) {

        Cookie cookie = new Cookie(name, value);
//        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true); -> 경로가 https: 이어야지 쿠키 생성

        response.addCookie(cookie);
    }

    public void deleteCookie(String name) {

        // 똑같은 이름의 쿠키를 보내면 최신걸로 대체됨
        Cookie cookie = new Cookie(name, "");
//        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);

        response.addCookie(cookie);
    }
}
