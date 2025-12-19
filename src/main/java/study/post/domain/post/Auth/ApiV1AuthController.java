package study.post.domain.post.Auth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.post.domain.post.RsData;
import study.post.domain.post.member.MemberDto;
import study.post.golbal.request.Rq;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ApiV1AuthController {

    private final AuthService authService;
    private final Rq rq;

    record LoginResBody(MemberDto member, String apiKey) {}

    record LoginReqBody(String userid, String password) {}
    record SignReqBody(String userid, String password, String nickname) {}

    @PostMapping("/login")
    public ResponseEntity<RsData<LoginResBody>> login(@RequestBody LoginReqBody reqBody,
                                                      HttpServletResponse response) {

        MemberDto member = authService.login(reqBody.userid, reqBody.password);
        rq.addCookie("apiKey", member.getApiKey());

        RsData<LoginResBody> rsData = new RsData<>("S-login",
                "로그인 성공",
                new LoginResBody(
                        member,
                        member.getApiKey()
                ));

        return ResponseEntity.status(200).body(rsData);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<RsData<Void>> logout() {

        rq.deleteCookie("apiKey");

        RsData<Void> rsData = new RsData<>("S-logout", "로그아웃 되었습니다.", null);

        return ResponseEntity.status(200).body(rsData);
    }

    @PostMapping("/signup")
    public ResponseEntity<RsData<MemberDto>> signup(@RequestBody SignReqBody reqBody) {

        MemberDto actor = authService.signup(reqBody.userid,reqBody.password, reqBody.nickname);

        RsData<MemberDto> rsData = new RsData<>("S-signup", "회원가입 완료", actor);
        return ResponseEntity.status(200).body(rsData);
    }


//    @PostMapping("/login")
    public ResponseEntity<RsData<Void>> loginV1(@RequestBody LoginReqBody reqBody) {

        // ID/PW로 로그인 처리 (apiKey X)
        authService.login(reqBody.userid, reqBody.password);

        RsData<Void> rsData = new RsData<>("S-login", "로그인 성공", null);
        return ResponseEntity.status(200).body(rsData);
    }
}
